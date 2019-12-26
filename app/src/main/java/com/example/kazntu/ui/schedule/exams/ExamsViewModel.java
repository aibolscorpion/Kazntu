package com.example.kazntu.ui.schedule.exams;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kazntu.data.AccountDao;
import com.example.kazntu.data.App;
import com.example.kazntu.data.AppDatabase;
import com.example.kazntu.data.entity.schedule.Exam;
import com.example.kazntu.data.network.KaznituRetrofit;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExamsViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<Exam>> examLiveData = new MutableLiveData<>();
    private List<Exam> examList = new ArrayList<>();
    private List<Exam> examListDB = new ArrayList<>();
    public ObservableBoolean getEmptyBoolean = new ObservableBoolean();



    private AppDatabase db = App.getInstance().getDatabase();
    private AccountDao accountDao = db.accountDao();
    //
    private BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(3);
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1,
            TimeUnit.SECONDS, queue);

    private ConnectivityManager connManager = (ConnectivityManager)App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
    private NetworkInfo activeNetwork = connManager.getActiveNetworkInfo();
    public void getExam(){
        // Поток для проверки БД
        executor.execute(() ->{
        if(connManager.getActiveNetworkInfo() != null && connManager.getActiveNetworkInfo().isAvailable() && activeNetwork.isConnected()){
            getExamListFromServer();
        }else {
            if (!accountDao.getExam().isEmpty()) { //Проверка локальной БД
                examListDB = accountDao.getExam();
                examLiveData.postValue(examListDB);

            }
        }
    });
    }

    private void getExamListFromServer() {
        KaznituRetrofit.getApi().updateExam().enqueue(new Callback<List<Exam>>() {
            @Override
            public void onResponse(Call<List<Exam>> call, Response<List<Exam>> response) {
                if (response.body() != null) {
                    examList = response.body();
                    examLiveData.setValue(examList);
                    getEmptyBoolean.set(examList.isEmpty());
                    new Thread(() -> {
                        insert(examList);
                    }).start();

                    System.out.println("#####response.body() EXAM == http 200 OK");
                }
            }

            @Override
            public void onFailure(Call<List<Exam>> call, Throwable t) {
                System.out.println("####Exam failure" + t.getMessage() + "&&&" + t.getCause());

            }
        });
    }

    MutableLiveData<List<Exam>> getExamLiveData(){
        if(examLiveData == null){
            examLiveData = new MutableLiveData<>();
        }
        return examLiveData;
    }


         private void insert(List<Exam> examList) {
        executor.execute(() -> {
            accountDao.deleteExam();
            accountDao.insertExam(examList);
        });
    }

}
