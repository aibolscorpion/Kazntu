package kz.almaty.satbayevuniversity.ui.schedule.scheduleFragment.studentsList;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.List;

import kz.almaty.satbayevuniversity.data.entity.schedule.Student;
import kz.almaty.satbayevuniversity.data.network.KaznituRetrofit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StudentsListViewModel extends ViewModel {
    MutableLiveData<List<Student>> liveData = new MutableLiveData<>();
    public void getNotificationListFromServer(int classid, String language){
        KaznituRetrofit.getApi().getStudentList(classid,language).enqueue(new Callback<List<Student>>() {
            @Override
            public void onResponse(Call<List<Student>> call, Response<List<Student>> response) {
                if(response.isSuccessful()){
                    liveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<List<Student>> call, Throwable t) {

            }
        });
    }

    public MutableLiveData<List<Student>> getLiveData() {

        return liveData;
    }
}
