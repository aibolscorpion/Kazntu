package kz.almaty.satbayevuniversity.ui.umkd.filefragment.fileDataFragment.webViewFragment;

import android.os.Environment;
import android.util.Log;

import androidx.databinding.ObservableBoolean;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import kz.almaty.satbayevuniversity.utils.Storage;
import kz.almaty.satbayevuniversity.data.network.KaznituRetrofit;

import org.apache.commons.io.IOUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WebViewViewModel extends ViewModel {
    private static final String TAG = "WebViewViewModel";
    private MutableLiveData<File> docFile = new MutableLiveData<>();

    public ObservableBoolean loadPB = new ObservableBoolean();

    // TODO: Implement the ViewModel

    void getFile(){
        loadPB.set(true);
        KaznituRetrofit.getApi().downloadFileCourse(
                Storage.getInstance().getCourseId())
                .enqueue(new Callback<ResponseBody>() {
                    @Override
                    public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                        if (response.body() != null) {

                            String fileName = Storage.getInstance().getFileName();
                        try {
                            File path = Environment.getExternalStorageDirectory();
                            File file = new File(path, fileName);
                            FileOutputStream fileOutputStream = new FileOutputStream(file);
                             IOUtils.write(response.body().bytes(), fileOutputStream);
                            docFile.setValue(file);
                        } catch (IOException e) {
                            Log.e(TAG, "Error while writing file!");
                            Log.e(TAG, e.toString());
                        }
                        loadPB.set(false);
                     }
                    }
                    @Override
                    public void onFailure(Call<ResponseBody> call, Throwable t) {
                    }
                });
    }

    MutableLiveData<File> getDownloadFileMutableLiveData(){
        if(docFile == null){
            docFile = new MutableLiveData<>();
        }
        return docFile;
    }

}
