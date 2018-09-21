package com.example.boris.smartorder3;


import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Base64;
import android.util.Log;
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


public class RecordFragment extends Fragment {
    private final static String TAG = "RecordFragment";
    TextView txtDashiResult,txtRichnessResult,txtGarlicResult,txtSpicyResult, txtTextureResult
            ,txtDrinkResult,txtDesertResult;
    Button btnPay;
    private CCommonTask ramenInsertTask;
    List<Integer> drinkAndDesertList = new ArrayList<>();



    public RecordFragment() {


    }

    public static Fragment newInstance() {
        RecordFragment fragment = new RecordFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.record, container, false);
        txtDashiResult = view.findViewById(R.id.txtDashiResult);
        txtRichnessResult =  view.findViewById(R.id.txtRichnessResult);
        txtGarlicResult =  view.findViewById(R.id.txtGarlicResult);
        txtSpicyResult =  view.findViewById(R.id.txtSpicyResult);
        txtTextureResult =  view.findViewById(R.id.txtTextureResult);
        txtDrinkResult = view.findViewById(R.id.txtDrinkResult);
        txtDesertResult = view.findViewById(R.id.txtDesertResult);

        SharedPreferences pref = getActivity().getSharedPreferences(CCommon.ORDER_INFO, MODE_PRIVATE);

        final String dashiResult = pref.getString("dashi", "");
        txtDashiResult.setText(dashiResult);

        final String richnessResult = pref.getString("richness", "");
        txtRichnessResult.setText(richnessResult);

        final String garlicResult = pref.getString("garlic", "");
        txtGarlicResult.setText(garlicResult);

        final String spicyResult = pref.getString("spicy", "");
        txtSpicyResult.setText(spicyResult);

        final String textureResult = pref.getString("texture", "");
        txtTextureResult.setText(textureResult);


        String drinkStr="";

    int matchaNumber=0,matchaLatteNumber=0,matchaSmoothieNumber=0,beerNumber=0;


        if(pref.getString("抹茶", "")!=""){
        drinkStr = drinkStr + pref.getString("抹茶", "") + "\n";
        String matcha = pref.getString("抹茶", "");
        if (matcha == "抹茶") {

            matchaNumber = 2;
            drinkAndDesertList.add(matchaNumber);

        }

        }


        if (pref.getString("抹茶拿鐵", "")!=""){
        drinkStr = drinkStr + pref.getString("抹茶拿鐵", "") + "\n";
        String matchaLatte = pref.getString("抹茶拿鐵", "");
        if (matchaLatte == "抹茶拿鐵") {

            matchaLatteNumber = 3;
            drinkAndDesertList.add(matchaLatteNumber);

        }


    } if (pref.getString("抹茶奶昔", "")!=""){
            drinkStr = drinkStr + pref.getString("抹茶奶昔", "") + "\n";
            String matchaSmoothie = pref.getString("抹茶奶昔", "");
            if (matchaSmoothie == "抹茶奶昔") {

                matchaSmoothieNumber = 4;
                drinkAndDesertList.add(matchaSmoothieNumber);


            }


    } if (pref.getString("啤酒", "")!=""){
            drinkStr = drinkStr + pref.getString("啤酒", "") + "\n";
            String beer = pref.getString("啤酒", "");
            if (beer == "啤酒") {

                beerNumber = 5;
                drinkAndDesertList.add(beerNumber);


            }


    }
    txtDrinkResult.setText(drinkStr);




    String desertStr="";
        int rainDropNumber=0,dangoNumber=0,cakeNumber=0,iceNumber=0;



        if(pref.getString("水信玄餅", "")!=""){
            desertStr = desertStr + pref.getString("水信玄餅", "") + "\n";
            String raindrop = pref.getString("水信玄餅", "");
            if (raindrop == "水信玄餅") {

                rainDropNumber = 6;
                drinkAndDesertList.add(rainDropNumber);

            }


        }


        if (pref.getString("糯米丸子", "")!=""){
            desertStr = desertStr + pref.getString("糯米丸子", "") + "\n";
            String dango = pref.getString("糯米丸子", "");
            if (dango == "糯米丸子") {

                dangoNumber = 7;
                drinkAndDesertList.add(dangoNumber);

            }


        } if (pref.getString("抹茶蛋糕", "")!=""){
            desertStr = desertStr + pref.getString("抹茶蛋糕", "") + "\n";

            String cake = pref.getString("抹茶蛋糕", "");
            if (cake == "抹茶蛋糕") {

                cakeNumber = 6;
                drinkAndDesertList.add(cakeNumber);

            }

        } if (pref.getString("抹茶冰淇淋", "")!=""){
            desertStr = desertStr + pref.getString("抹茶冰淇淋", "") + "\n";


            String ice = pref.getString("抹茶冰淇淋", "");
            if (ice == "抹茶冰淇淋") {

                iceNumber = 6;
                drinkAndDesertList.add(iceNumber);

            }
        }

        txtDesertResult.setText(desertStr);



        btnPay = view.findViewById(R.id.btnPay);
        btnPay.setOnClickListener(new View.OnClickListener() {
            String dashiNumber,richnessNumber,garlicNumber,spicyNumber,textNumber;

            @Override
            public void onClick(View v) {
                String ramenNumberToData = dashiNumber+ richnessNumber+garlicNumber+spicyNumber+textNumber;

                if (ramenNumberToData.length() < 5) {
                    new AlertDialog.Builder(getActivity())
                            .setTitle("拉麵客制尚未選擇完整")
                            .setMessage("")
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            })

                            .show();

                }else{


                    switch (dashiResult){

                    case "淡":

                        dashiNumber = "0";
                        break;

                    case "中":

                        dashiNumber = "1";
                        break;

                    case  "濃":

                        dashiNumber = "2";
                        break;

                }  switch (richnessResult){

                    case "淡":

                        richnessNumber = "0";
                        break;

                    case "中":

                        richnessNumber = "1";
                        break;

                    case  "濃":

                        richnessNumber = "2";
                        break;

                }switch (garlicResult){

                    case "淡":

                        garlicNumber = "0";
                        break;

                    case "中":

                        garlicNumber = "1";
                        break;

                    case  "濃":

                        garlicNumber = "2";
                        break;

                }switch (spicyResult){

                    case "小辣":

                        spicyNumber = "0";
                        break;

                    case "中辣":

                        spicyNumber = "1";
                        break;

                    case  "大辣":

                        spicyNumber = "2";
                        break;

                }switch (textureResult){

                    case "軟":

                        textNumber = "0";
                        break;

                    case "普通":

                        textNumber = "1";
                        break;

                    case  "硬":

                        textNumber = "2";
                        break;
                }



                    SharedPreferences pref = getActivity().getSharedPreferences(CCommon.LOGIN_INFO, MODE_PRIVATE);

                    String phone = pref.getString("account", "");

                    Result result = new Result(phone,ramenNumberToData,drinkAndDesertList, table);




                    if (CCommon.isNetworkConnected(getActivity())) {
                        String url = CCommon.URL + "/SmartOrderServlet";

                        JsonObject jsonObject = new JsonObject();
                        jsonObject.addProperty("action", "ramenInsert");
                        jsonObject.addProperty("ramenNumberToData",ramenNumberToData);
                        jsonObject.addProperty("drinkAndDesertList",drinkAndDesertList);



                        int count = 0;

                        try {
                            String result = new CCommonTask(url, jsonObject.toString()).execute().get();
                            count = Integer.valueOf(result);

                        } catch (Exception e) {

                            Log.e(TAG, e.toString());
                        }
                        if (count == 0) {


                        } else {


                        }
                    } else {


                    }
            }

            }
    });

        return view;
    }








}
