package com.example.boris.smartorder3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Timer;


public class UserActivityReservationFragment_tb2 extends Fragment {

    public static final String STATUS_KEY = "status";//等候狀態
    public static final String NAME_KEY = "name";
    public static final String TIME_KEY = "time";
    public static final String TAG = "Firestore";

    //假資料
    private String userName = "Easom";
    private int status = 0; //(0 ->尚未取號)
    private Date date = new Date();

    TextView mNmber;
    Button mgetNumber;
    // 初始化 FirebaseFirestore 指定集合路徑
    private DocumentReference documentReference = FirebaseFirestore.getInstance().document("smartOrder/waiting")  ;


    public static Fragment newInstance(){
        UserActivityReservationFragment_tb2 fragment = new UserActivityReservationFragment_tb2();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_reservation_fragment_tb2,null);
        getNumber(view);
        return view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

/*
    public void fecthData(View view){

    }
*/
    public void getNumber (View view) {

        mgetNumber = view.findViewById(R.id.bt_getNumber);
        mNmber = view.findViewById(R.id.number);
        mgetNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                status = 1; //1 -> 取號
                Map<String, Object> dataToSave = new HashMap<String, Object>();
                dataToSave.put(STATUS_KEY,status);
                dataToSave.put(NAME_KEY, userName);
                dataToSave.put(TIME_KEY, date.toString());
                documentReference.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG,"檔案已儲存");
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG,"儲存失敗",e);
                    }
                });
            }
        });

        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if(documentSnapshot.exists()){
                    //MData mData = documentSnapshot.toObject(MData.class);
//                    String massageText = documentSnapshot.getString(MESSAGE_KEY);
//                    String nameText = documentSnapshot.getString(NAME_KEY);
                    mNmber.setText(documentSnapshot.getString(TIME_KEY));
                }
            }
        }); // 可以加addOnFailureListener() 失敗的監聽器

    }

}

