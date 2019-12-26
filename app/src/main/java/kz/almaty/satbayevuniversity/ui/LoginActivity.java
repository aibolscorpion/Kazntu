package kz.almaty.satbayevuniversity.ui;

import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProviders;


import kz.almaty.satbayevuniversity.databinding.ActivityLoginBinding;

import kz.almaty.satbayevuniversity.AuthViewModel;
import kz.almaty.satbayevuniversity.R;
import kz.almaty.satbayevuniversity.utils.Storage;
import kz.almaty.satbayevuniversity.data.AccountDao;
import kz.almaty.satbayevuniversity.data.App;
import kz.almaty.satbayevuniversity.data.AppDatabase;
import kz.almaty.satbayevuniversity.data.entity.AccountEntity;

import br.com.simplepass.loading_button_lib.customViews.CircularProgressButton;

public class LoginActivity extends AppCompatActivity {
    public AuthViewModel authViewModel;
    private CircularProgressButton loginBtn;
    private AppDatabase db = App.getInstance().getDatabase();
    private AccountDao accountDao = db.accountDao();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityLoginBinding activityLoginBinding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
        activityLoginBinding.setLifecycleOwner(this);
        activityLoginBinding.setViewModel(authViewModel);

        loginBtn = activityLoginBinding.loginBtn;

        if (savedInstanceState == null){
            authViewModel.initAuth();
        }

        authViewModel.getUserMutableLiveData().observe(this, this::doIntent);

        authViewModel.toastGetMessage().observe(this, aBoolean -> {
            if (aBoolean){
                Toast.makeText(this, "Заполните пустые поля", Toast.LENGTH_SHORT).show();
                revertBtn();
            }
        });

        authViewModel.getHandleError().observe(this, integer -> {
            switch (integer){
                case 1:
                    Toast.makeText(this, "Ошибка соединения. Пожалуйста, попробуйте еще раз", Toast.LENGTH_SHORT).show();
                    revertBtn();
                    break;
                case 2:
                    Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show();
                    revertBtn();
                    break;
                case 3:
                    Toast.makeText(this, "Неизвестная ошибка", Toast.LENGTH_SHORT).show();
                    revertBtn();
                    break;
            }
        });

        authViewModel.getHandleError().observe(this, integer -> {
            switch (integer){
                case 200:
                    loginBtn.doneLoadingAnimation(Color.parseColor("#6D67AE"),
                            BitmapFactory.decodeResource(getResources(), R.drawable.ic_done_white_48dp));
                    break;
                case 404:
                    Toast.makeText(this, "Not Found 404 Error", Toast.LENGTH_SHORT).show();
                    revertBtn();
                    break;
                case 400:
                    Toast.makeText(this, "Неверный логин или пароль", Toast.LENGTH_SHORT).show();
                    revertBtn();
                    break;
                case 500:
                    Toast.makeText(this, "Внутренняя ошибка сервера", Toast.LENGTH_SHORT).show();
                    revertBtn();
                    break;
            }
        });

        ConnectivityManager connManager =
                (ConnectivityManager)this.
                        getSystemService(Context.CONNECTIVITY_SERVICE);

        accountDao.getAll().observe(this, accountEntity -> {
            if(accountEntity != null){
                doIntent(accountEntity);
            } else{
                loginBtn.setOnClickListener(v -> {

                    if(connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                            loginBtn.startAnimation();
                            authViewModel.getInformation();
                        } else Toast.makeText(this, "Отсутствует подключение к интернету", Toast.LENGTH_SHORT).show();
                    hideSoftKeyboard();
                });
            }
        });
    }


    @Override
    protected void onPostResume() {
        super.onPostResume();
        revertBtn();
    }

    void doIntent(AccountEntity accountEntity){
        Storage.getInstance().setToken(accountEntity.getAccess_token());
        Storage.getInstance().setAccountFullName(accountEntity.getFullName());
        Storage.getInstance().setUsername(accountEntity.getUsername());
        Intent intent_name = new Intent(getApplicationContext(), HomeActivity.class);
        startActivity(intent_name);
        finish();
    }

    private void revertBtn(){
        loginBtn.revertAnimation();
    }


    protected void hideSoftKeyboard() {
        View view = this.getCurrentFocus();
        InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
        if (view !=null){
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}