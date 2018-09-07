package com.example.boris.smartorder3;


import android.app.Activity;
import android.app.ActivityManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class CCommon {
    public static final String URL = "http://10.0.2.2:8080";

    public static boolean isNetworkConnected(Activity activity) {
        NetworkInfo networkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return (networkInfo != null) && networkInfo.isConnected();

    }

}
