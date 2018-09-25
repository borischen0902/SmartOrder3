package com.example.boris.smartorder3;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;

import static android.content.Context.MODE_PRIVATE;


public class RecordFragment extends Fragment {
    TextView txtDashiResult,txtRichnessResult,txtGarlicResult,txtSpicyResult, txtTextureResult
            ,txtDrinkResult,txtDesertResult;
    Button btnPay;
    //菜單更新前置
    public static final String TIME_KEY = "time"; //更新產生新的時間戳
    private FirebaseFirestore db = FirebaseFirestore.getInstance();// 初始化 FirebaseFirestore
    private String documentPatch = "/smartOrder/update";//指定檔案路徑
    public static final String TAG = "FirebaseDeBug";

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
        txtDesertResult = view.findViewById(R.id.txtDesertResult);
        btnPay = view.findViewById(R.id.btnPay);

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


    String drinkStr="";

    if(pref.getString("抹茶", "")!=""){
        drinkStr = drinkStr + pref.getString("抹茶", "") + "\n";

    }
    if (pref.getString("抹茶拿鐵", "")!=""){
        drinkStr = drinkStr + pref.getString("抹茶拿鐵", "") + "\n";

    } if (pref.getString("抹茶奶昔", "")!=""){
            drinkStr = drinkStr + pref.getString("抹茶奶昔", "") + "\n";

    } if (pref.getString("啤酒", "")!=""){
            drinkStr = drinkStr + pref.getString("啤酒", "") + "\n";
    }
    txtDrinkResult.setText(drinkStr);


        String desertStr="";

        if(pref.getString("水信玄餅", "")!=""){
            desertStr = desertStr + pref.getString("水信玄餅", "") + "\n";

        }
        if (pref.getString("糯米丸子", "")!=""){
            desertStr = desertStr + pref.getString("糯米丸子", "") + "\n";

        } if (pref.getString("抹茶蛋糕", "")!=""){
            desertStr = desertStr + pref.getString("抹茶蛋糕", "") + "\n";

        } if (pref.getString("抹茶冰淇淋", "")!=""){
            desertStr = desertStr + pref.getString("抹茶冰淇淋", "") + "\n";
        }
        txtDesertResult.setText(desertStr);

        updateMenu();

        return view;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);





    }

    //更新菜單資訊- 資料庫確定更新後呼叫
    private void updateMenu(){
        db.document(documentPatch)
                .update(TIME_KEY, FieldValue.serverTimestamp())
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "菜單資訊更新成功!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "菜單資訊更新失敗", e);
                    }
                });
    }
}
