package com.example.boris.smartorder3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


public class UserActivityReservationFragment_tb2 extends Fragment {

    //設定資料KEY值
    public static final String ACCOUNT_KEY = "account";
    public static final String STATUS_KEY = "status";//等候狀態
    public static final String SEVERAL_KEY = "several";//人數
    public static final String TIME_KEY = "time";
    public static final String NUMBER_KEY = "number";
    public static final String TAG = "FirebaseDeBug";


    private String account; //設定檔取得帳號
    private Button mGetNumber ,buttonTest;
    private TextView textView,textView_order,mNumber;
    private SeekBar seekBar;
    private LinearLayout linearLayout;
    private int waitingNum;
    private long myNumber;
    private int mySeveral;
    private ListenerRegistration listenCollection , listenOrderBy ,listenTable;


    // 初始化 FirebaseFirestore 指定集合路徑

    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN);
    private String DateString = dateFormat.format(new Date());
    private String collectionPatch = "smartOrder/waiting/" + DateString;

    public static Fragment newInstance() {
        UserActivityReservationFragment_tb2 fragment = new UserActivityReservationFragment_tb2();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_reservation_fragment_tb2, null);
        handViews(view);
        //清除設定檔用
        buttonTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setPreferences(STATUS_KEY,0);
            }
        });
        readAccountPreferences();//取得設定檔的登入帳號
        checksStatus();
        SeekBarChange();
        ListenCollection();
        ButtonClick();
        return view;
    }
    //確認候位狀態
    private void checksStatus() {
        if (getPreferences(STATUS_KEY) == 1){
            visibilityChange();
            mySeveral = getPreferences(SEVERAL_KEY);
            myNumber = getPreferences(NUMBER_KEY);
            mNumber.setText("人數("+ mySeveral+")您的號碼為:" + myNumber);
            ListenOrderBy();
        }
        if (getPreferences(STATUS_KEY) == 2){
            visibilityChange();
            mNumber.setText("");
            textView_order.setText("您的桌號為:" + getPreferences("table"));
        }
    }

    private void handViews(final View view) {
        textView = view.findViewById(R.id.TextView_several);
        textView_order = view.findViewById(R.id.textView_order);
        seekBar = view.findViewById(R.id.seekBar);
        mGetNumber = view.findViewById(R.id.bt_getNumber);
        mNumber = view.findViewById(R.id.number);
        linearLayout = view.findViewById(R.id.LinearLayout);
        buttonTest = view.findViewById(R.id.ButtonTest);
    }
    //SeekBar 選擇候位人數
    private void SeekBarChange() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {

            @Override
            public void onProgressChanged(SeekBar seekBar,
                                          int progress,
                                          boolean fromUser) {
                textView.setText(String.valueOf(progress));
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }
    //監聽號碼取到幾號
    private void ListenCollection(){
        Query query = db.collection(collectionPatch);
        listenCollection = query.addSnapshotListener(MetadataChanges.INCLUDE,new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value,
                                        @Nullable FirebaseFirestoreException e) {
                        if (e != null) {
                            waitingNum = 1;
                            Log.w(TAG, "檔案讀取失敗:", e);
                            return;
                        }
                        if (value != null) {
                            waitingNum = value.size();
                            if(waitingNum == 0 )
                                waitingNum = 1;
                        }else waitingNum = 1;
                    }
                });
    }
    //點取(取號) 執行getNumber();
    private void ButtonClick(){
        mGetNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getNumber();
            }
        });
    }
    //取得號碼
    private void getNumber(){
        myNumber = waitingNum;
        mySeveral = seekBar.getProgress();
        Map<String, Object> data = new HashMap<>();
        data.put(NUMBER_KEY, myNumber);
        data.put(STATUS_KEY, 1);
        data.put(ACCOUNT_KEY, account);
        data.put(SEVERAL_KEY,mySeveral);
        data.put(TIME_KEY, FieldValue.serverTimestamp());

        db.collection(collectionPatch)
                .add(data)
                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                    @Override
                    public void onSuccess(DocumentReference documentReference) {
                        Log.d(TAG, "DocumentSnapshot written with ID: " + documentReference.getId());
                        mNumber.setText("人數("+ mySeveral+")您的號碼為:" + myNumber);
                        listenCollection.remove();//取消號碼監聽
                        visibilityChange();
                        setPreferences(STATUS_KEY,1);
                        setPreferences(NUMBER_KEY,(int)myNumber);
                        setPreferences(SEVERAL_KEY,mySeveral);
                        ListenOrderBy();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "Error adding document", e);
                    }
                });
    }
    //隱藏取號按鈕及人數選擇
    private  void visibilityChange(){
        linearLayout.setVisibility(View.INVISIBLE);
        mGetNumber.setVisibility(View.INVISIBLE);
    }

    //存入設定檔
    private void setPreferences(String key,int value){
        FragmentActivity act = getActivity();
        Log.d(TAG, "目前FragmentActivity: " + act);
        SharedPreferences pref = act.getSharedPreferences(CCommon.WAITING_INFO, act.MODE_PRIVATE);
        pref.edit().putInt(key,value).apply();
        act.setResult(act.RESULT_OK);
    }
    //讀取設定檔
    private int getPreferences(String key){
        FragmentActivity act = getActivity();
        SharedPreferences pref = act.getSharedPreferences(CCommon.WAITING_INFO, getContext().MODE_PRIVATE);
        return pref.getInt(key,0);
    }
    //讀取帳號
    private void readAccountPreferences() {
        FragmentActivity act = getActivity();
        SharedPreferences pref = act.getSharedPreferences(CCommon.LOGIN_INFO, act.MODE_PRIVATE);
        account = pref.getString("account", "未登入");
    }

    //監聽目前號碼
    private void ListenOrderBy() {
        DocumentReference docRef = db.collection(collectionPatch).document("NUMBER");
        listenOrderBy = docRef.addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "讀取號碼失敗.", e);
                    return;
                }

                if(snapshot.getLong(NUMBER_KEY) == null){
                    Map<String, Object> number = new HashMap<>();
                    number.put(NUMBER_KEY, 1);
                    db.collection(collectionPatch).document("NUMBER")
                            .set(number);
                }

                if (snapshot != null && snapshot.exists()) {
                    long a = snapshot.getLong(NUMBER_KEY);
                    textView_order.setText("目前號碼為:"+ a);
                    Log.d(TAG, "目前號碼為: " + snapshot.getLong(NUMBER_KEY));
                    if (a == myNumber){
                        textView_order.setText("等候桌面清潔中");
                        getTable();
                    }
                } else {
                    Log.d(TAG, "目前號碼為空");
                }
            }
        });
    }
    //到號設定座位
    private void getTable(){
        listenOrderBy.remove();//取消監聽號碼
        Query query = db.collection("smartOrder/waiting/table").whereEqualTo(STATUS_KEY,0);
        listenTable = query.addSnapshotListener(MetadataChanges.INCLUDE,new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "檔案讀取失敗:", e);
                    return;
                }
                for (QueryDocumentSnapshot doc : value) {
                    if (doc.get("table") != null) {
                        long table = doc.getLong("table");
                        Log.d(TAG, "取得桌號為" + table);
                        textView_order.setText("您的桌號為:" + table);
                        setPreferences("table", (int)(table));
                        mNumber.setText("");
                        setPreferences(STATUS_KEY,2);
                        sittingDown((int)table);
                        listenTable.remove();
                        changeNumber(myNumber);
                        break;
                    }
                }

            }
        });

    }
    //跳號
    private void changeNumber(long myNumber) {
        db.collection(collectionPatch).document("NUMBER")
                .update("number", myNumber+1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "號碼更新成功!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "號碼更新失敗", e);
                    }
                });
    }
    //設定桌子狀態為有人
    private void sittingDown(int table){
        db.collection("smartOrder/waiting/table").document(String.valueOf(table))
                .update(STATUS_KEY, 1)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        Log.d(TAG, "座位狀態更新成功!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.w(TAG, "座位狀態更新失敗", e);
                    }
                });
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}