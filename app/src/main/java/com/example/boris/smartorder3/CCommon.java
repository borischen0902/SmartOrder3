package com.example.boris.smartorder3;


import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;


public class CCommon {
    public final static String LOGIN_INFO = "SmartOrderLoginInfo";
    public final static String ORDER_INFO = "SmartOrderOrderInfo";
    public final static  String URL = "http://10.0.2.2:8080/SmartOrder_Web";

    public static boolean isNetworkConnected(Activity activity) {
        NetworkInfo networkInfo;
        ConnectivityManager connectivityManager = (ConnectivityManager) activity.getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager != null ? connectivityManager.getActiveNetworkInfo() : null;
        return (networkInfo != null) && networkInfo.isConnected();
    }

}
