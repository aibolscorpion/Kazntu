package kz.almaty.satbayevuniversity.ui.grade.transcriptFragment;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import kz.almaty.satbayevuniversity.data.AccountDao;
import kz.almaty.satbayevuniversity.data.App;
import kz.almaty.satbayevuniversity.data.AppDatabase;
import kz.almaty.satbayevuniversity.data.entity.grade.transcript.ResponseTranscript;
import kz.almaty.satbayevuniversity.data.entity.grade.transcript.SemestersItem;
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

public class TranscriptViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private List<SemestersItem> semestersItems = new ArrayList<>();
    private List<SemestersItem> semestersItemsDB = new ArrayList<>();

    private MutableLiveData<List<SemestersItem>> transcriptLiveData = new MutableLiveData<>();

    private AppDatabase db = App.getInstance().getDatabase();
    private AccountDao accountDao = db.accountDao();
    public ObservableBoolean loadRv = new ObservableBoolean();
    public ObservableBoolean getEmptyBoolean = new ObservableBoolean();

    private MutableLiveData<Boolean> handleTimeout = new MutableLiveData<>();
    //
    private BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(3);
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1,
            TimeUnit.SECONDS, queue);

    private ConnectivityManager connManager = (ConnectivityManager)App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    private NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();

    public void getTranscript(){
        loadRv.set(true);
        executor.execute(() ->{
            if(connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo().isAvailable() && activeNetwork.isConnected()){
                getSemesterItemListFromServer();
            } else {
                if (!accountDao.getSemestersItem().isEmpty()) { //Проверка локальной БД
                    semestersItemsDB = accountDao.getSemestersItem();
                    loadRv.set(false);
                    transcriptLiveData.postValue(semestersItemsDB);
                }
            }
        });
    }

    private void getSemesterItemListFromServer() {
        loadRv.set(true);
        KaznituRetrofit.getApi().updateTranscript().enqueue(new Callback<ResponseTranscript>() {
            @Override
            public void onResponse(Call<ResponseTranscript> call, Response<ResponseTranscript> response) {
                if (response.isSuccessful()) {
                    semestersItems = response.body().getSemesters();
                    transcriptLiveData.setValue(semestersItems);
                    getEmptyBoolean.set(semestersItems.isEmpty());
                    new Thread(() -> {
                        insert(semestersItems);
                    }).start();
                    loadRv.set(false);
                }
            }

            @Override
            public void onFailure(Call<ResponseTranscript> call, Throwable t) {
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

    MutableLiveData<List<SemestersItem>> getTranscriptLiveData(){
        if(transcriptLiveData == null){
            transcriptLiveData = new MutableLiveData<>();
        }
        return transcriptLiveData;
    }

    MutableLiveData<Boolean> getHandleTimeout(){
        if (handleTimeout == null){
            handleTimeout = new MutableLiveData<>();
        }
        return handleTimeout;
    }



    private void insert(List<SemestersItem> semestersItemList) {
        executor.execute(() -> {
            accountDao.deleteSemestersItem();
            accountDao.insertSemestersItem(semestersItemList);
        });
    }

    private void exception(){
        loadRv.set(false);
        handleTimeout.setValue(true);
        getEmptyBoolean.set(true);
    }
}