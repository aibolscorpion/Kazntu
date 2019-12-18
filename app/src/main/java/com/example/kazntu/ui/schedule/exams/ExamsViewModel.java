package com.example.kazntu.ui.schedule.exams;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.kazntu.data.AccountDao;
import com.example.kazntu.data.App;
import com.example.kazntu.data.AppDatabase;
import com.example.kazntu.data.entity.grade.transcript.SemestersItem;
import com.example.kazntu.utils.Storage;
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

    public void updateExam(){
        // Поток для проверки БД
        executor.execute(() ->{
            if (!accountDao.getExam().isEmpty()) { //Проверка локальной БД
                examListDB = accountDao.getExam();
                examLiveData.postValue(examListDB);

                KaznituRetrofit.getApi().updateExam().enqueue(new Callback<List<Exam>>() {
                    @Override
                    public void onResponse(Call<List<Exam>> call, Response<List<Exam>> response) {
                        if (response.body() != null) {
                            examList = response.body();
                            System.out.println("#####response.body() EXAM == http 200 OK");
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Exam>> call, Throwable t) {
                        System.out.println("####Exam failure" + t.getMessage() + "&&&" + t.getCause());
                    }
                });


                if (examListDB.containsAll(examList)){
                } else{
                    examLiveData.postValue(examList);
                }
            } else {
                getConnection();

            }
    });
    }

    private void getConnection() {
        KaznituRetrofit.getApi().updateExam().enqueue(new Callback<List<Exam>>() {
            @Override
            public void onResponse(Call<List<Exam>> call, Response<List<Exam>> response) {
                if (response.body() != null) {
                    examList = response.body();
                    examLiveData.setValue(examList);
                    getEmptyBoolean.set(examList.isEmpty());
                    compareLists(examList);
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


    private void compareLists(List<Exam> examList){
        new Thread(() -> {
            if (accountDao.getExam().isEmpty()){
                insert(examList);
            }   else {
                update(examList);
            }
        }).start();
    }
    private void insert(List<Exam> examList) {
        executor.execute(() -> accountDao.insertExam(examList));
    }

    private void update(List<Exam> examList) {
        executor.execute(() -> accountDao.updateExam(examList));
    }
}
