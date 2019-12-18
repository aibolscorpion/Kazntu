package com.example.kazntu.data;

import android.app.Application;
import android.content.Context;

import androidx.room.Room;

import com.example.kazntu.utils.LocaleHelper;

public class App extends Application {

    public static App instance;
    private static Context mContext;

    private AppDatabase database;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder(this, AppDatabase.class, "database")
                .fallbackToDestructiveMigration()
                .build();
        mContext = getApplicationContext();
    }

    public static App getInstance() {
        return instance;
    }

    public static Context getContext() {
        return mContext;
    }



    public AppDatabase getDatabase() {
        return database;
    }

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(LocaleHelper.onAttach(base, "ru"));
    }
}