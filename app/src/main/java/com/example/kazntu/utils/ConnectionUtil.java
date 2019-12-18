package com.example.kazntu.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import androidx.lifecycle.LiveData;

public class ConnectionUtil extends LiveData<Integer> {

    private Context context;

    public ConnectionUtil(Context context) {
        this.context = context;
    }

//    @Override
//    protected void onActive() {
//        super.onActive();
//        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
//        context.registerReceiver(networkReceiver, filter);
//    }
//
//    @Override
//    protected void onInactive() {
//        super.onInactive();
//        context.unregisterReceiver(networkReceiver);
//    }

    public void checkConnection(){

//            if(intent.getExtras()!=null) {
//                Bundle extras = intent.getExtras();
//                NetworkInfo info = extras.getParcelable("networkInfo");
//                NetworkInfo.State state = info.getState();
//                if (info.getState() == state.CONNECTED) {
//                    postValue(true);
//
//                } else {
//                    postValue(false);
//                }
                ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
//                NetworkInfo info = connectivityManager.getActiveNetworkInfo();
                   if(connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState() == NetworkInfo.State.CONNECTED ||
                            connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState() == NetworkInfo.State.CONNECTED) {
                       postValue(1);
                   }
//                Bundle extras = intent.getExtras();

//                NetworkInfo info = extras
//                        .getParcelable("networkInfo");
//
//                NetworkInfo.State state = info.getState();
//
//                if (state == NetworkInfo.State.CONNECTED) {
//                   postValue(true);
//
                 else {
                     postValue(2);
                }



    };

}
