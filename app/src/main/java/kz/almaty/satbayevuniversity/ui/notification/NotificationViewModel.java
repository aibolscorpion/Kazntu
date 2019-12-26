package kz.almaty.satbayevuniversity.ui.notification;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import kz.almaty.satbayevuniversity.data.AccountDao;
import kz.almaty.satbayevuniversity.data.App;
import kz.almaty.satbayevuniversity.data.AppDatabase;
import kz.almaty.satbayevuniversity.data.entity.notification.Notification;
import kz.almaty.satbayevuniversity.data.network.KaznituRetrofit;

import java.io.IOException;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationViewModel extends ViewModel {
    public ObservableBoolean getEmptyBoolean = new ObservableBoolean();

    private MutableLiveData<List<Notification>> notificationMutableLiveData = new MutableLiveData<>();
    private MutableLiveData<Boolean> handleTimeout = new MutableLiveData<>();

    private List<Notification> emptyList = new ArrayList<>();
    private List<Notification> emptyListDB = new ArrayList<>();

    private AppDatabase db = App.getInstance().getDatabase();
    private AccountDao accountDao = db.accountDao();

    public ObservableBoolean loadRv = new ObservableBoolean();

    private BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(3);
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1,
            TimeUnit.SECONDS, queue);
    private ConnectivityManager connManager = (ConnectivityManager)App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    private NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();

    void getNotification(){
        loadRv.set(true);
        // Поток для проверки БД
        executor.execute(() ->{
            if(connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo().isAvailable() && activeNetwork.isConnected()){
                getNotificationListFromServer();
            } else {
                if (!accountDao.getNews().isEmpty()) { //Проверка локальной БД
                    emptyListDB = accountDao.getNews();
                    notificationMutableLiveData.postValue(emptyListDB);
                    loadRv.set(false);
                }
            }
        });
        }

    private void getNotificationListFromServer(){
        KaznituRetrofit.getApi().updateNotification().enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if (response.isSuccessful()) {
                    System.out.println("####### retry Connection");
                    emptyList = response.body();
                    getEmptyBoolean.set(emptyList.isEmpty());
                    notificationMutableLiveData.setValue(emptyList);// сохранил данные с retrofit чтобы обзервить

                    new Thread(() -> {
                        insert(emptyList);
                    }).start();

                    loadRv.set(false);
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                if (t instanceof SocketTimeoutException)
                {
                    exception();
                }
                else if (t instanceof IOException)
                {
                    exception();
                }
                else if( t instanceof SocketException){
                    exception();
                }
            }
        });
    }

    MutableLiveData<List<Notification>> getNotificationMutableLiveData() {
        if(notificationMutableLiveData == null){
            notificationMutableLiveData = new MutableLiveData<>();
        }
        return notificationMutableLiveData;
    }

    MutableLiveData<Boolean> getHandleTimeout(){
        if (handleTimeout == null){
            handleTimeout = new MutableLiveData<>();
        }
        return handleTimeout;
    }

    private void exception(){
        loadRv.set(false);
        handleTimeout.setValue(true);
        getEmptyBoolean.set(true);
    }

    private void insert(List<Notification> news) {
        executor.execute(() ->{
            accountDao.deleteNotification();
            accountDao.insertNews(news);
            System.out.println("####### insert News");
        });
    }
}
