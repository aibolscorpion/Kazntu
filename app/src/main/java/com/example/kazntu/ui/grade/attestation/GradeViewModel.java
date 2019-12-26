package com.example.kazntu.ui.grade.attestation;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kazntu.utils.Storage;
import com.example.kazntu.data.AccountDao;
import com.example.kazntu.data.App;
import com.example.kazntu.data.AppDatabase;
import com.example.kazntu.data.entity.grade.attestation.Attestation;
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


public class GradeViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<Attestation>> attestationLiveDate = new MutableLiveData<>();
    private List<Attestation> attestationList = new ArrayList<>();
    private List<Attestation> attestationListDB = new ArrayList<>();

    private AppDatabase db = App.getInstance().getDatabase();
    private AccountDao accountDao = db.accountDao();

    public ObservableBoolean getEmptyBoolean = new ObservableBoolean();
    public ObservableBoolean loadRv = new ObservableBoolean();

    private MutableLiveData<Boolean> handleTimeout = new MutableLiveData<>();

    //
    private BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(3);
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1,
            TimeUnit.SECONDS, queue);
    private ConnectivityManager connManager = (ConnectivityManager)App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    private NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();

    void getAttestation() {
        loadRv.set(true);
        // Поток для проверки БД
        executor.execute(() -> {
            if(connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo().isAvailable() && activeNetwork.isConnected()){
                getGradeListFromServer();
            } else {
                if (!accountDao.getAttestation().isEmpty()) { //Проверка локальной БД
                    attestationListDB = accountDao.getAttestation();
                    attestationLiveDate.postValue(attestationListDB);
                    loadRv.set(false);
                }
            }
        });
    }

    private void getGradeListFromServer(){
        loadRv.set(true);

        KaznituRetrofit.getApi().updateAttestation().enqueue(new Callback<List<Attestation>>() {
            @Override
            public void onResponse(Call<List<Attestation>> call, Response<List<Attestation>> response) {
                if (response.isSuccessful()) {
                    attestationList = response.body();
                    attestationLiveDate.postValue(attestationList);
                    getEmptyBoolean.set(attestationList.isEmpty());
                    new Thread(() -> {
                        insert(attestationList);
                    }).start();
                    loadRv.set(false);
                }
            }
            @Override
            public void onFailure(Call<List<Attestation>> call, Throwable t) {
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


        MutableLiveData<List<Attestation>> getAttestationLiveDate() {
            if (attestationLiveDate == null) {
                attestationLiveDate = new MutableLiveData<>();
            }
            return attestationLiveDate;
        }

    MutableLiveData<Boolean> getHandleTimeout(){
        if (handleTimeout == null){
            handleTimeout = new MutableLiveData<>();
        }
        return handleTimeout;
    }



    private void insert(List<Attestation> attestationList) {
        executor.execute(() -> {
            accountDao.deleteAttestation();
            accountDao.insertAttestation(attestationList);
        });
    }

    private void exception(){
        loadRv.set(false);
        handleTimeout.setValue(true);
        getEmptyBoolean.set(true);
    }
    }