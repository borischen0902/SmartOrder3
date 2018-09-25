package com.example.boris.smartorder3;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.internal.service.Common;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.protobuf.StringValue;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class RamenFragment extends Fragment {
    RadioGroup rdgDashi,rdgRichness,rdgGarlic,rdgSpicy,rdgTexture;
    int dashi,richness,garlic,spicy,texture;
    private FragmentActivity activity;
    private FragmentManager fragmentManager;
    private final static String TAG = "RamenUploadFragment";


    public RamenFragment() {

    }

    public static Fragment newInstance() {
        RamenFragment fragment;
        fragment = new RamenFragment();
        return fragment;
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_ramen, container, false);
        customized(view);
        Button btnRamenConfirm = view.findViewById(R.id.btnRamenConfirm);
        btnRamenConfirm.setOnClickListener(confirmListener);
        activity = getActivity();
        rdgDashi = view.findViewById(R.id.rdgDashi);
        rdgRichness = view.findViewById(R.id.rdgRichness);
        rdgGarlic = view.findViewById(R.id.rdgGarlic);
        rdgSpicy = view.findViewById(R.id.rdgSpicy);
        rdgTexture = view.findViewById(R.id.rdgTexture);
        return view;

    }

    private Button.OnClickListener confirmListener = new Button.OnClickListener() {
        @Override
        public void onClick(final View v) {
            new AlertDialog.Builder(getActivity())
                            .setTitle("加入至付款？")
                            .setMessage("餐點選購完畢，請至付款，完成點餐")
                            .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    SharedPreferences pref = getActivity().getSharedPreferences(CCommon.RAMEN_INFO, MODE_PRIVATE);

                                    if (dashi ==0) {

                                        pref.edit().putString("dashi", "淡").apply();
                                        getActivity().setResult(RESULT_OK);

                                    } else if (dashi == 1){


                                        pref.edit().putString("dashi", "中").apply();
                                        getActivity().setResult(RESULT_OK);



                                    } else if (dashi == 2) {

                                        pref.edit().putString("dashi", "濃").apply();
                                        getActivity().setResult(RESULT_OK);

                                    }

                                    if (richness == 0 ){

                                        pref.edit().putString("richness", "淡").apply();
                                        getActivity().setResult(RESULT_OK);

                                    }else if (richness == 1) {

                                        pref.edit().putString("richness", "中").apply();
                                        getActivity().setResult(RESULT_OK);

                                    }else if (richness == 2) {

                                        pref.edit().putString("richness", "濃").apply();
                                        getActivity().setResult(RESULT_OK);

                                    }if (garlic == 0 ){

                                        pref.edit().putString("garlic", "淡").apply();
                                        getActivity().setResult(RESULT_OK);

                                    }else if (garlic == 1) {

                                        pref.edit().putString("garlic", "中").apply();
                                        getActivity().setResult(RESULT_OK);

                                    }else if (garlic == 2) {

                                        pref.edit().putString("garlic", "濃").apply();
                                        getActivity().setResult(RESULT_OK);

                                    }if (spicy == 0 ){

                                        pref.edit().putString("spicy", "小辣").apply();
                                        getActivity().setResult(RESULT_OK);

                                    }else if (spicy == 1) {

                                        pref.edit().putString("spicy", "中辣").apply();
                                        getActivity().setResult(RESULT_OK);

                                    }else if (spicy == 2) {

                                        pref.edit().putString("spicy", "大辣").apply();
                                        getActivity().setResult(RESULT_OK);

                                    }if (texture == 0 ){

                                        pref.edit().putString("texture", "軟").apply();
                                        getActivity().setResult(RESULT_OK);

                                    }else if (texture == 1) {

                                        pref.edit().putString("texture", "普通").apply();
                                        getActivity().setResult(RESULT_OK);

                                    }else if (texture == 2) {

                                        pref.edit().putString("texture", "硬").apply();
                                        getActivity().setResult(RESULT_OK);

                                    }


                                }
                            })

                            .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {


                                }
                            })
                            .show();

            }


    };




    public void customized(View view)  {


        RadioGroup rdgDashi = view.findViewById(R.id.rdgDashi);
        rdgDashi.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch(checkedId) {
                    case R.id.btnDashiLight:
                        dashi = 0;

                            break;

                    case R.id.btnDashiMedium:
                        dashi =1;

                            break;

                    case R.id.btnDashiStrong:
                        dashi =2;

                            break;

                }


            }

        });


        RadioGroup rdgRichness = view.findViewById(R.id.rdgRichness);
        rdgRichness.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {


                switch (checkedId) {

                    case R.id.btnRichnessLight:
                        richness = 0;

                        break;

                    case R.id.btnRichnessMedium:
                        richness = 1;

                        break;



                    case R.id.btnRichnessStrong:
                        richness = 2;

                        break;



                }

            }
        });


        RadioGroup rdgGarlic = view.findViewById(R.id.rdgGarlic);
        rdgGarlic.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.btnGarlicLight:
                        garlic = 0;

                        break;

                    case R.id.btnGarlicMedium:
                        garlic = 1;

                        break;

                    case R.id.btnGarlicStrong:
                        garlic = 2;

                        break;

                }

            }
        });



        RadioGroup rdgSpicy = view.findViewById(R.id.rdgSpicy);
        rdgSpicy.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.btnSpicyLight:
                        spicy = 0;

                        break;

                    case R.id.btnSpicyMedium:
                        spicy =1;

                        break;

                    case R.id.btnSpicyStrong:
                        spicy=2;

                        break;

                }

            }
        });

        RadioGroup rdgTexture = view.findViewById(R.id.rdgTexture);
        rdgTexture.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

                switch (checkedId) {

                    case R.id.btnTextureSoft:
                        texture = 0;

                        break;

                    case R.id.btnTextureMedium:
                        texture= 1;

                        break;

                    case R.id.btnTextureFirm:
                        texture = 2;

                        break;

                }

            }
        });





    }

}



