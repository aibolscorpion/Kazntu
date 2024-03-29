package kz.almaty.satbayevuniversity;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.databinding.ObservableField;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import kz.almaty.satbayevuniversity.data.AccountDao;
import kz.almaty.satbayevuniversity.data.App;
import kz.almaty.satbayevuniversity.data.AppDatabase;
import kz.almaty.satbayevuniversity.data.entity.AccountEntity;
import kz.almaty.satbayevuniversity.data.entity.LoginFields;
import kz.almaty.satbayevuniversity.data.network.KaznituRetrofit;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AuthViewModel extends ViewModel {
    public ObservableField<String> password = new ObservableField<>();
    public ObservableField<String> username = new ObservableField<>();

    private MutableLiveData<AccountEntity> userMutableLiveData;
    private MutableLiveData<Bitmap> drawableMutableLiveData;
    private MutableLiveData<Boolean> getMessage = new MutableLiveData<>();
    private MutableLiveData<Integer> handleError;

    private MutableLiveData<Integer> handleTimeout = new MutableLiveData<>();

    private AppDatabase db = App.getInstance().getDatabase();
    private AccountDao accountDao = db.accountDao();

    private BlockingQueue<Runnable> queue = new ArrayBlockingQueue<>(3);
    private ThreadPoolExecutor executor = new ThreadPoolExecutor(1, 1, 1,
            TimeUnit.SECONDS, queue);




    public void initAuth(){
        userMutableLiveData = new MutableLiveData<>();
        drawableMutableLiveData = new MutableLiveData<>();
        handleError = new MutableLiveData<>();
    }

    public void getInformation() {
        String name = username.get();
        String psw = password.get();
        if ((name == null || name.isEmpty()) || (psw == null || psw.isEmpty())) {
            getMessage.setValue(true);
        } else {
        LoginFields loginFields = new LoginFields(username.get(), password.get());
        final RequestBody requestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("username", loginFields.getUsername())
                .addFormDataPart("password", loginFields.getPassword()).build();

        KaznituRetrofit.getApi().onLogin(requestBody).enqueue(new Callback<AccountEntity>() {
            @Override
            public void onResponse(Call<AccountEntity> call, final Response<AccountEntity> response) {
                switch (response.code()) {
                    case 200:
                        handleError.setValue(200);
                        addAccount(response.body());
                        userMutableLiveData.setValue(response.body());
                        break;
                    case 404:
                        handleError.setValue(404);
                        break;
                    case 400:
                        handleError.setValue(400);
                        break;
                    case 500:
                        handleError.setValue(500);
                        break;
                }
            }

            @Override
            public void onFailure(Call<AccountEntity> call, Throwable t) {
                if (t instanceof SocketTimeoutException)
                {
                    handleTimeout.setValue(1);
                }
                else if (t instanceof IOException)
                {
                    handleTimeout.setValue(2);
                }
                else if (t instanceof UnknownHostException){
                    handleTimeout.setValue(3);
                }
            }
        });
     }

    }

    public MutableLiveData<AccountEntity> getUserMutableLiveData(){
        if(userMutableLiveData == null){
            userMutableLiveData = new MutableLiveData<>();
        }
        return userMutableLiveData;
    }

    public MutableLiveData<Integer> getHandleError(){
        if (handleError == null){
            handleError = new MutableLiveData<>();
        }
        return handleError;
    }

    public void getImageUrl(){
        KaznituRetrofit.getApi().updatePhoto().enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                if(response.body() != null){
                    Bitmap bmp = BitmapFactory.decodeStream(response.body().byteStream());
                    drawableMutableLiveData.setValue(bmp);
                }
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
            }
        });
    }

    public MutableLiveData<Bitmap> getDrawable(){
        if (drawableMutableLiveData == null){
            drawableMutableLiveData = new MutableLiveData<>();
        }
        return drawableMutableLiveData;
    }

    public MutableLiveData<Boolean> toastGetMessage(){
        return getMessage;
    }

    public MutableLiveData<Integer> getHandleTimeout(){
        return handleTimeout;
    }

    @BindingAdapter({"imageUrl"})
    public static void loadImage(ImageView imageView, Bitmap bitmap){
        Glide.with(imageView.getContext())
                .setDefaultRequestOptions(new RequestOptions()
                        .circleCrop())
                .load(bitmap)
                .placeholder(R.drawable.user_icon)
                .into(imageView);
    }

    private void addAccount(AccountEntity accountEntity){
        executor.execute(() -> accountDao.insert(accountEntity));
    }

    public void clearDB() {
        Runnable r = () -> {
            accountDao.deleteResponseJournal();
            accountDao.delete();
            accountDao.deleteSchedule();
            accountDao.deleteExam();
            accountDao.deleteAttestation();
            accountDao.deleteSemestersItem();
            accountDao.deleteUmkd();
            accountDao.deleteNotification();
        };
        executor.execute(r);
    }

}