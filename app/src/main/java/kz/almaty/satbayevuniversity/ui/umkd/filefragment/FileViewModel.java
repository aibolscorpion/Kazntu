package kz.almaty.satbayevuniversity.ui.umkd.filefragment;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import kz.almaty.satbayevuniversity.utils.Storage;
import kz.almaty.satbayevuniversity.data.entity.umkd.File;
import kz.almaty.satbayevuniversity.data.network.KaznituRetrofit;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FileViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<List<File>> fileMutableLiveData = new MutableLiveData<>();
    public ObservableBoolean getEmptyBoolean = new ObservableBoolean();
    private List<File> files = new ArrayList<>();


    void getFile(){
        KaznituRetrofit.getApi().updateFileCourse(
                Storage.getInstance().getCourseCode(),
                Storage.getInstance().getInstructorID())
                .enqueue(new Callback<List<File>>() {
                    @Override
                    public void onResponse(Call<List<File>> call, Response<List<File>> response) {
                        files = response.body();
                        fileMutableLiveData.setValue(files);
                        getEmptyBoolean.set(files.isEmpty());
                    }

                    @Override
                    public void onFailure(Call<List<File>> call, Throwable t) {

                    }
                });
    }

    public MutableLiveData<List<File>> getFileMutableLiveData(){
        if(fileMutableLiveData == null){
            fileMutableLiveData = new MutableLiveData<>();
        }
        return fileMutableLiveData;
    }
}
