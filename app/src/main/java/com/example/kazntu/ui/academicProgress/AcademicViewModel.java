package com.example.kazntu.ui.academicProgress;


import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kazntu.data.AccountDao;
import com.example.kazntu.data.App;
import com.example.kazntu.data.AppDatabase;
import com.example.kazntu.data.entity.academic.ResponseJournal;
import com.example.kazntu.data.network.KaznituRetrofit;

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

public class AcademicViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<ResponseJournal>> academicData = new MutableLiveData<>();
    private List<ResponseJournal> responseJournalList = new ArrayList<>();
    private List<ResponseJournal> responseJournalListForDB = new ArrayList<>();

    public ObservableBoolean getEmptyBoolean = new ObservableBoolean();
    public ObservableBoolean loadRv = new ObservableBoolean();

    private MutableLiveData<Boolean> toast = new MutableLiveData<>();
    private MutableLiveData<Integer> handleError = new MutableLiveData<>();
    private MutableLiveData<Integer> handleTimeout = new MutableLiveData<>();

    private AppDatabase db = App.getInstance().getDatabase();
    private AccountDao accountDao = db.accountDao();

    private BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(3);
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1,
            TimeUnit.SECONDS, queue);

    void getJournal() {

        loadRv.set(true);

        // Поток для проверки БД
        executor.execute(() ->{
            if (!accountDao.getResponseJournal().isEmpty()) { //Проверка локальной БД

                responseJournalListForDB = accountDao.getResponseJournal();
                loadRv.set(false);
                academicData.postValue(responseJournalListForDB);

                KaznituRetrofit.getApi().updateJournal().enqueue(new Callback<List<ResponseJournal>>() {
                    @Override
                    public void onResponse(Call<List<ResponseJournal>> call, Response<List<ResponseJournal>> response) {
                        switch (response.code()) {
                            case 200:
                                responseJournalList = response.body();
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
                    public void onFailure(Call<List<ResponseJournal>> call, Throwable t) {
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

                if (responseJournalListForDB.containsAll(responseJournalList)){
                } else{
                    academicData.postValue(responseJournalList);
                }
            }
            else {
                getConnection();
            }
        });
    }

     private void getConnection(){

        toast.postValue(true);
        loadRv.set(true);
        KaznituRetrofit.getApi().updateJournal().enqueue(new Callback<List<ResponseJournal>>() {
            @Override
            public void onResponse(Call<List<ResponseJournal>> call, Response<List<ResponseJournal>> response) {
                switch (response.code()) {
                    case 200:
                        responseJournalList = response.body();
                        getEmptyBoolean.set(responseJournalList.isEmpty());
                        academicData.setValue(responseJournalList);// сохранил данные с retrofit чтобы обзервить
                        compareLists(responseJournalList);
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
            public void onFailure(Call<List<ResponseJournal>> call, Throwable t) {
                if (t instanceof SocketTimeoutException)
                {
                    exception();
                }
                else if (t instanceof IOException)
                {
                    exception();
                }
                else if(t instanceof SocketException){
                    exception();
                }
            }
        });
    }

    MutableLiveData<Boolean> getToast(){
        if (toast == null){
            toast = new MutableLiveData<>();
        }
        return toast;
    }

    MutableLiveData<Integer> getHandleTimeout(){
        if (handleTimeout == null){
            handleTimeout = new MutableLiveData<>();
        }
        return handleTimeout;
    }

    MutableLiveData<List<ResponseJournal>> getAcademicData(){
        if(academicData == null){
            academicData = new MutableLiveData<>();
        }
        return academicData;
    }

    MutableLiveData<Integer> getHandleError(){
        if(handleError == null){
            handleError = new MutableLiveData<>();
        }
        return handleError;
    }

    private void compareLists(List<ResponseJournal> responseJournals){
        new Thread(() -> {
            if (accountDao.getResponseJournal().isEmpty()){
                insert(responseJournals);
                System.out.println("#######insert");
            }   else {
                update(responseJournals);
                System.out.println("#######update");
            }
        }).start();
    }

    private void insert(List<ResponseJournal> responseJournals) {
        executor.execute(() -> accountDao.insertResponseJournal(responseJournals));
    }

    private void update(List<ResponseJournal> responseJournals) {
        executor.execute(() -> accountDao.updateJournal(responseJournals));
    }
    private void exception(){
        loadRv.set(false);
        handleTimeout.setValue(1);
        getEmptyBoolean.set(true);
    }
}
