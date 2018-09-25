package com.example.boris.smartorder3;


import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;
import static android.content.Context.MODE_PRIVATE;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.wallet.AutoResolveHelper;
import com.google.android.gms.wallet.IsReadyToPayRequest;
import com.google.android.gms.wallet.PaymentData;
import com.google.android.gms.wallet.PaymentDataRequest;
import com.google.android.gms.wallet.PaymentsClient;
import com.google.android.gms.wallet.Wallet;
import com.google.android.gms.wallet.WalletConstants;
import java.util.Optional;
import java.util.concurrent.ExecutionException;

import org.json.JSONObject;


public class RecordFragment extends Fragment {
    private final static String TAG = "RecordFragment";
    TextView txtDashiResult, txtRichnessResult, txtGarlicResult, txtSpicyResult, txtTextureResult, txtDrinkResult, txtDesertResult;
    Button btnPay;
    List<Integer> drinkAndDesertList = new ArrayList<>();
    private PaymentsClient mPaymentsClient;
    private View mGooglePayButton;
    private static final int LOAD_PAYMENT_DATA_REQUEST_CODE = 42;


    public RecordFragment() {


    }

    public static Fragment newInstance() {
        RecordFragment fragment;
        fragment = new RecordFragment();
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.record, container, false);
        txtDashiResult = view.findViewById(R.id.txtDashiResult);
        txtRichnessResult = view.findViewById(R.id.txtRichnessResult);
        txtGarlicResult = view.findViewById(R.id.txtGarlicResult);
        txtSpicyResult = view.findViewById(R.id.txtSpicyResult);
        txtTextureResult = view.findViewById(R.id.txtTextureResult);
        txtDrinkResult = view.findViewById(R.id.txtDrinkResult);
        txtDesertResult = view.findViewById(R.id.txtDesertResult);

        final SharedPreferences ramenPref = getActivity().getSharedPreferences(CCommon.RAMEN_INFO, MODE_PRIVATE);
        final SharedPreferences drinkPref = getActivity().getSharedPreferences(CCommon.DRINK_INFO, MODE_PRIVATE);
        final SharedPreferences desertPref = getActivity().getSharedPreferences(CCommon.DESERT_INFO, MODE_PRIVATE);


        final String dashiResult = ramenPref.getString("dashi", "");
        txtDashiResult.setText(dashiResult);

        final String richnessResult = ramenPref.getString("richness", "");
        txtRichnessResult.setText(richnessResult);

        final String garlicResult = ramenPref.getString("garlic", "");
        txtGarlicResult.setText(garlicResult);

        final String spicyResult = ramenPref.getString("spicy", "");
        txtSpicyResult.setText(spicyResult);

        final String textureResult = ramenPref.getString("texture", "");
        txtTextureResult.setText(textureResult);


        String drinkStr = "";

        int matchaNumber = 0, matchaLatteNumber = 0, matchaSmoothieNumber = 0, beerNumber = 0;


        if (!drinkPref.getString("抹茶", "").equals("")) {
            drinkStr = drinkStr + drinkPref.getString("抹茶", "") + "\n";

            String matcha = drinkPref.getString("抹茶", "");
            if (matcha.equals("抹茶")) {

                matchaNumber = 2;
                drinkAndDesertList.add(matchaNumber);

            }

        }


        if (!drinkPref.getString("抹茶拿鐵", "").equals("")) {
            drinkStr = drinkStr + drinkPref.getString("抹茶拿鐵", "") + "\n";
            String matchaLatte = drinkPref.getString("抹茶拿鐵", "");
            if (matchaLatte.equals("抹茶拿鐵")) {

                matchaLatteNumber = 3;
                drinkAndDesertList.add(matchaLatteNumber);

            }


        }
        if (!drinkPref.getString("抹茶奶昔", "").equals("")) {
            drinkStr = drinkStr + drinkPref.getString("抹茶奶昔", "") + "\n";
            String matchaSmoothie = drinkPref.getString("抹茶奶昔", "");
            if (matchaSmoothie.equals("抹茶奶昔")) {

                matchaSmoothieNumber = 4;
                drinkAndDesertList.add(matchaSmoothieNumber);


            }


        }
        if (!drinkPref.getString("啤酒", "").equals("")) {
            drinkStr = drinkStr + drinkPref.getString("啤酒", "") + "\n";
            String beer = drinkPref.getString("啤酒", "");
            if (beer.equals("啤酒")) {

                beerNumber = 5;
                drinkAndDesertList.add(beerNumber);


            }


        }
        txtDrinkResult.setText(drinkStr);


        String desertStr = "";
        int rainDropNumber = 0, dangoNumber = 0, cakeNumber = 0, iceNumber = 0;


        if (!desertPref.getString("水信玄餅", "").equals("")) {
            desertStr = desertStr + desertPref.getString("水信玄餅", "") + "\n";
            String raindrop = desertPref.getString("水信玄餅", "");
            if (raindrop.equals("水信玄餅")) {

                rainDropNumber = 6;
                drinkAndDesertList.add(rainDropNumber);

            }


        }


        if (!desertPref.getString("糯米丸子", "").equals("")) {
            desertStr = desertStr + desertPref.getString("糯米丸子", "") + "\n";
            String dango = desertPref.getString("糯米丸子", "");
            if (dango.equals("糯米丸子")) {

                dangoNumber = 7;
                drinkAndDesertList.add(dangoNumber);

            }


        }
        if (!desertPref.getString("抹茶蛋糕", "").equals("")) {
            desertStr = desertStr + desertPref.getString("抹茶蛋糕", "") + "\n";

            String cake = desertPref.getString("抹茶蛋糕", "");
            if (cake.equals("抹茶蛋糕")) {

                cakeNumber = 8;
                drinkAndDesertList.add(cakeNumber);

            }

        }
        if (!desertPref.getString("抹茶冰淇淋", "").equals("")) {
            desertStr = desertStr + desertPref.getString("抹茶冰淇淋", "") + "\n";


            String ice = desertPref.getString("抹茶冰淇淋", "");
            if (ice.equals("抹茶冰淇淋")) {

                iceNumber = 9;
                drinkAndDesertList.add(iceNumber);

            }
        }

        txtDesertResult.setText(desertStr);


        btnPay = view.findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            String dashiNumber, richnessNumber, garlicNumber, spicyNumber, textNumber;
            String ramenNumberToData = dashiNumber + richnessNumber + garlicNumber + spicyNumber + textNumber;


            @Override
            public void onClick(View v) {

                switch (dashiResult) {

                    case "淡":

                        dashiNumber = "0";
                        break;

                    case "中":

                        dashiNumber = "1";
                        break;

                    case "濃":

                        dashiNumber = "2";
                        break;

                }
                switch (richnessResult) {

                    case "淡":

                        richnessNumber = "0";
                        break;

                    case "中":

                        richnessNumber = "1";
                        break;

                    case "濃":

                        richnessNumber = "2";
                        break;

                }
                switch (garlicResult) {

                    case "淡":

                        garlicNumber = "0";
                        break;

                    case "中":

                        garlicNumber = "1";
                        break;

                    case "濃":

                        garlicNumber = "2";
                        break;

                }
                switch (spicyResult) {

                    case "小辣":

                        spicyNumber = "0";
                        break;

                    case "中辣":

                        spicyNumber = "1";
                        break;

                    case "大辣":

                        spicyNumber = "2";
                        break;

                }
                switch (textureResult) {

                    case "軟":

                        textNumber = "0";
                        break;

                    case "普通":

                        textNumber = "1";
                        break;

                    case "硬":

                        textNumber = "2";
                        break;
                }

                SharedPreferences phonePref = getActivity().getSharedPreferences(CCommon.LOGIN_INFO, MODE_PRIVATE);
                SharedPreferences tablePref = getActivity().getSharedPreferences(CCommon.WAITING_INFO, MODE_PRIVATE);


                String phone = phonePref.getString("account", "");
                int table = tablePref.getInt("table", 0);
                ramenNumberToData = dashiNumber + richnessNumber + garlicNumber + spicyNumber + textNumber;


                Result result = new Result(phone, ramenNumberToData, drinkAndDesertList, table);
                if (CCommon.isNetworkConnected(getActivity())) {
                    String url = CCommon.URL + "/SmartOrderServlet";

                    JsonObject jsonObject = new JsonObject();
                    jsonObject.addProperty("action", "resultInsert");
                    Gson gson = new Gson();
                    jsonObject.addProperty("result", gson.toJson(result));
                    String jsonOut = jsonObject.toString();
                    CCommonTask showResultTask = new CCommonTask(url, jsonOut);
                    try {
                        showResultTask.execute().get();

                    } catch (InterruptedException | ExecutionException e) {

                        e.printStackTrace();

                    }


                }


            }


        });

        return view;

    }

}



