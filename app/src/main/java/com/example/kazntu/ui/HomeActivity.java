package com.example.kazntu.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.databinding.DataBindingUtil;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.kazntu.AuthViewModel;
import com.example.kazntu.R;

import com.example.kazntu.data.SharedPrefCache;
import com.example.kazntu.data.entity.Language;
import com.example.kazntu.utils.Storage;
import com.example.kazntu.databinding.ActivityHomeBinding;
import com.example.kazntu.databinding.NavHeaderBinding;
import com.example.kazntu.ui.academicProgress.MainAcademicFragment;
import com.example.kazntu.ui.settings.SettingsFragment;
import com.example.kazntu.ui.umkd.UmkdFragment;
import com.google.android.material.navigation.NavigationView;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.util.Locale;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    public static final String FRAGMENT_FIRST = "FRAGMENT_FIRST";
    public static final String FRAGMENT_SECOND = "FRAGMENT_SECOND";
    public static final String FRAGMENT_THIRD = "FRAGMENT_THIRD";
    private float scaleFactor = 5f;
    private NavigationView navigationView;

    public DrawerLayout drawer;

    public AuthViewModel authViewModel;

    public ActivityHomeBinding activityHomeBinding;
    private NavHeaderBinding navHeaderBinding;

    public HomeActivity() {
    }

    public void setLocale(String lang) {
        Locale myLocale = new Locale(lang);
        Resources res = getResources();
        DisplayMetrics dm = res.getDisplayMetrics();
        Configuration conf = res.getConfiguration();
        conf.locale = myLocale;
        res.updateConfiguration(conf, dm);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupBindings(savedInstanceState);

        SharedPrefCache sharedPrefCache = new SharedPrefCache();
        String sharedString = sharedPrefCache.getStr("language",this);
        Gson gson = new Gson();
        try{
        Language language1 = gson.fromJson(sharedString, Language.class);
        setLocale(language1.getLanguageCode());
        } catch (IllegalStateException | JsonSyntaxException ignored){}


    }

    public void getMenuText(){
        Menu menu = navigationView.getMenu();
        MenuItem academicProgress = menu.findItem(R.id.academicProgress);
        academicProgress.setTitle(R.string.academicProgress);

        MenuItem umkd = menu.findItem(R.id.umkd);
        umkd.setTitle(R.string.umkd);

        MenuItem settings = menu.findItem(R.id.settings);
        settings.setTitle(R.string.settings);

        MenuItem logout = menu.findItem(R.id.logout);
        logout.setTitle(R.string.btn_login_exit);
    }

    private void setupBindings(Bundle savedInstanceState) {
        authViewModel = ViewModelProviders.of(this).get(AuthViewModel.class);
        activityHomeBinding = DataBindingUtil.setContentView(this, R.layout.activity_home);

        navHeaderBinding = DataBindingUtil.inflate(getLayoutInflater(), R.layout.nav_header,
                activityHomeBinding.navigationView, false);
        activityHomeBinding.navigationView.addHeaderView(navHeaderBinding.getRoot());

        navHeaderBinding.setAccountEntity(Storage.getInstance());
        authViewModel.getImageUrl();

        drawer = findViewById(R.id.drawer);
        drawer.setScrimColor(Color.TRANSPARENT);

        navigationView = findViewById(R.id.navigationView);
        navigationView.setItemIconTintList(null);
        navigationView.setNavigationItemSelectedListener(this);
        onNavigationItemSelected(navigationView.getMenu().getItem(0));
        navigationView.getMenu().getItem(0).setChecked(true);

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, R.string.open, R.string.close){
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                super.onDrawerSlide(drawerView, slideOffset);
                float slideX = drawerView.getWidth() * slideOffset;
                activityHomeBinding.fragmentContainer.setTranslationX(slideX);
                activityHomeBinding.fragmentContainer.setScaleX(1 - (slideOffset / scaleFactor));
                activityHomeBinding.fragmentContainer.setScaleY(1 - (slideOffset / scaleFactor));
            }
        };
        drawer.setDrawerElevation(0f);
        drawer.addDrawerListener(actionBarDrawerToggle);

        if (savedInstanceState == null){
            authViewModel.initAuth();
        }
        setupUpdate();
    }

    private void setupUpdate() {
        authViewModel.getDrawable().observe(this, bitmap -> {
            navHeaderBinding.setImageUrl(bitmap);
        });
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.academicProgress:
                replaceFragment(MainAcademicFragment.newInstance(),FRAGMENT_FIRST, R.id.fragment_container);
                break;
            case R.id.umkd:
                replaceFragment(UmkdFragment.newInstance(), FRAGMENT_SECOND, R.id.fragment_container);
                break;
            case R.id.settings:
                replaceFragment(new SettingsFragment(getApplicationContext()), "3", R.id.fragment_container);
                break;
            case R.id.logout:
                exit();
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void exit(){
        authViewModel.clearDB();
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
        finish();
    }

    private void replaceFragment(Fragment newFragment, String tag, int container) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(container, newFragment, tag)
                .commit();
    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    public void OpenToggleNavMenu() {
        drawer.openDrawer(GravityCompat.START);
    }
}

