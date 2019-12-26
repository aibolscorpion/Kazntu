package kz.almaty.satbayevuniversity.ui.umkd;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import kz.almaty.satbayevuniversity.data.AccountDao;
import kz.almaty.satbayevuniversity.data.App;
import kz.almaty.satbayevuniversity.data.AppDatabase;
import kz.almaty.satbayevuniversity.data.entity.umkd.Umkd;
import kz.almaty.satbayevuniversity.data.network.KaznituRetrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UmkdViewModel extends ViewModel {

    public ObservableBoolean loadRv = new ObservableBoolean();
    public ObservableBoolean listVisibile = new ObservableBoolean();

    private List<Umkd> umkdList = new ArrayList<>();
    private List<Umkd> umkdListDB = new ArrayList<>();

    public ObservableBoolean getEmptyBoolean = new ObservableBoolean();

    private MutableLiveData<List<Umkd>> umkdMutableLiveData = new MutableLiveData<>();

    private AppDatabase db = App.getInstance().getDatabase();
    private AccountDao accountDao = db.accountDao();

    private BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(3);
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1,
            TimeUnit.SECONDS, queue);

    void getUmkd(){
        listVisibile.set(false);
        loadRv.set(true);

        executor.execute(() -> {
            if (!accountDao.getUmkd().isEmpty()) { //Проверка локальной БД
                umkdListDB = accountDao.getUmkd();
                loadRv.set(false);
                listVisibile.set(true);

                KaznituRetrofit.getApi().updateInstructor().enqueue(new Callback<List<Umkd>>() {
                    @Override
                    public void onResponse(Call<List<Umkd>> call, Response<List<Umkd>> response) {
                        umkdList = response.body();
                        loadRv.set(false);
                        listVisibile.set(true);
                    }

                    @Override
                    public void onFailure(Call<List<Umkd>> call, Throwable t) {
                    }
                });

                if (umkdListDB.containsAll(umkdList)){
                    umkdMutableLiveData.postValue(umkdListDB);
                } else{
                    umkdMutableLiveData.postValue(umkdList);
                }
            }
            else {
                KaznituRetrofit.getApi().updateInstructor().enqueue(new Callback<List<Umkd>>() {
                    @Override
                    public void onResponse(Call<List<Umkd>> call, Response<List<Umkd>> response) {
                        umkdList = response.body();
                        umkdMutableLiveData.setValue(umkdList);
                        getEmptyBoolean.set(umkdList.isEmpty());
                        new Thread(() -> {
                            accountDao.insertUmkd(umkdList);
                        }).start();
                        loadRv.set(false);
                        listVisibile.set(true);
                    }

                    @Override
                    public void onFailure(Call<List<Umkd>> call, Throwable t) {
                    }
                });
             }
        });
    }

    MutableLiveData<List<Umkd>> getUmkdMutableLiveData() {
        if(umkdMutableLiveData == null){
            umkdMutableLiveData = new MutableLiveData<>();
        }
        return umkdMutableLiveData;
    }
}
