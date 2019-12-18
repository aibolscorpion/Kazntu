package com.example.kazntu.ui.schedule.scheduleFragment;

import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kazntu.data.AccountDao;
import com.example.kazntu.data.App;
import com.example.kazntu.data.AppDatabase;
import com.example.kazntu.data.entity.academic.ResponseJournal;
import com.example.kazntu.utils.Storage;
import com.example.kazntu.data.entity.schedule.Schedule;
import com.example.kazntu.data.network.KaznituRetrofit;

import java.io.IOException;
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

public class ScheduleViewModel extends ViewModel {
    private List<Schedule> scheduleList = new ArrayList<>();
    private List<Schedule> scheduleListFromDb = new ArrayList<>();
    private MutableLiveData<List<Schedule>> scheduleLiveData = new MutableLiveData<>();

    public ObservableBoolean loadRv = new ObservableBoolean();

    private MutableLiveData<Integer> handleTimeout = new MutableLiveData<>();

    private MutableLiveData<Integer> handleError = new MutableLiveData<>();

    private AppDatabase db = App.getInstance().getDatabase();
    private AccountDao accountDao = db.accountDao();

    private BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(3);
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1,
            TimeUnit.SECONDS, queue);
    //

    public void getSchedule() {

        loadRv.set(true);

        executor.execute(() ->{

            if (accountDao.getSchedule().size() != 0) { //Проверка локальной БД
                scheduleListFromDb = accountDao.getSchedule();
                System.out.println("list " + scheduleListFromDb.size());
                loadRv.set(false);
                scheduleLiveData.postValue(scheduleListFromDb);

                KaznituRetrofit.getApi().updateSchedule().enqueue(new Callback<List<Schedule>>() {
                    @Override
                    public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
                        if (response.isSuccessful()) {
                            Log.i("aibolscorpion","onResponse = "+response.body().toString());
                            scheduleList = response.body();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Schedule>> call, Throwable t) {
                        if (t instanceof SocketTimeoutException)
                        {
                            handleTimeout.setValue(1);
                        }
                        else if (t instanceof IOException)
                        {
                            handleTimeout.setValue(1);
                        }
                    }
                });

                if (scheduleListFromDb.containsAll(scheduleList)){
                } else{
                    scheduleLiveData.postValue(scheduleList);
                }
            } else{
                retryConnection();
            }

        });
    }


    private void retryConnection(){
        loadRv.set(true);
        KaznituRetrofit.getApi().updateSchedule().enqueue(new Callback<List<Schedule>>() {
            @Override
            public void onResponse(Call<List<Schedule>> call, Response<List<Schedule>> response) {
                switch (response.code()) {
                    case 200:
                        scheduleList = response.body();
                        Log.i("aibolscorpion","schedule from server = "+scheduleList.toString());
                        scheduleLiveData.setValue(scheduleList);// сохранил данные с retrofit чтобы обзервить
                        compareLists(scheduleList);
                        loadRv.set(false);
                        break;
                    case 404:
                        handleError.setValue(404);
                        break;
                    case 400:
                        handleError.setValue(400);
                        break;
                    case 401:
                        handleError.setValue(401);
                        break;
                    case 500:
                        handleError.setValue(500);
                        break;
                }
            }
            @Override
            public void onFailure(Call<List<Schedule>> call, Throwable t) {
            }
        });
    }


    MutableLiveData<List<Schedule>> getScheduleLiveData(){
        if(scheduleLiveData==null){
            scheduleLiveData = new MutableLiveData<>();
        }
        return scheduleLiveData;
    }

    MutableLiveData<Integer> getHandleError(){
        if(handleError == null){
            handleError = new MutableLiveData<>();
        }
        return handleError;
    }

    MutableLiveData<Integer> getHandleTimeout(){
        if (handleTimeout == null){
            handleTimeout = new MutableLiveData<>();
        }
        return handleTimeout;
    }

    private void compareLists(List<Schedule> scheduleList){
        new Thread(() -> {
            if (accountDao.getSchedule().isEmpty()){
                insert(scheduleList);
            }   else {
                update(scheduleList);
            }
        }).start();
    }

    private void insert(List<Schedule> scheduleList) {
        executor.execute(() -> accountDao.insertSchedule(scheduleList));
    }

    private void update(List<Schedule> scheduleList) {
        executor.execute(() -> accountDao.updateSchedule(scheduleList));
    }
}
