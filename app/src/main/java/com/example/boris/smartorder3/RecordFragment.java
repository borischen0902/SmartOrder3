package com.example.boris.smartorder3;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;

import static android.content.Context.MODE_PRIVATE;


public class RecordFragment extends Fragment {
    TextView txtDashiResult,txtRichnessResult,txtGarlicResult,txtSpicyResult, txtTextureResult
            ,txtDrinkResult;


    public RecordFragment() {


    }

    public static Fragment newInstance() {
        RecordFragment fragment = new RecordFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_menu_record, container, false);
        txtDashiResult = view.findViewById(R.id.txtDashiResult);
        txtRichnessResult =  view.findViewById(R.id.txtRichnessResult);
        txtGarlicResult =  view.findViewById(R.id.txtGarlicResult);
        txtSpicyResult =  view.findViewById(R.id.txtSpicyResult);
        txtTextureResult =  view.findViewById(R.id.txtTextureResult);
        txtDrinkResult = view.findViewById(R.id.txtDrinkResult);

        SharedPreferences pref = getActivity().getSharedPreferences(CCommon.ORDER_INFO, MODE_PRIVATE);

        String dashiResult = pref.getString("dashi", "");
        txtDashiResult.setText(dashiResult);

        String richnessResult = pref.getString("richness", "");
        txtRichnessResult.setText(richnessResult);

        String garlicResult = pref.getString("garlic", "");
        txtGarlicResult.setText(garlicResult);

        String spicyResult = pref.getString("spicy", "");
        txtSpicyResult.setText(spicyResult);

        String textureResult = pref.getString("texture", "");
        txtTextureResult.setText(textureResult);


    String str="";

    if(pref.getString("抹茶", "")!=""){
        str = str + pref.getString("抹茶", "") + "\n";

    }
    if (pref.getString("抹茶拿鐵", "")!=""){
        str = str + pref.getString("抹茶拿鐵", "") + "\n";

    } if (pref.getString("抹茶奶昔", "")!=""){
        str = str + pref.getString("抹茶奶昔", "") + "\n";

    } if (pref.getString("啤酒", "")!=""){
        str = str + pref.getString("啤酒", "") + "\n";
    }
    txtDrinkResult.setText(str);

    return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }
}
