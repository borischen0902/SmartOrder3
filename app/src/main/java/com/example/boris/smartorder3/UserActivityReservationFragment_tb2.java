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
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;



import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;


public class UserActivityReservationFragment_tb2 extends Fragment {

    public static final String NAME_KEY = "name";
    public static final String STATUS_KEY = "status";//等候狀態
    public static final String SEVERAL_KEY ="several";//人數
    public static final String TIME_KEY = "time";
    public static final String NUMBER_KEY = "number";
    public static final String TAG = "FirebaseDeBug";



    //假資料
    private String userName = "Eason";
    private int status = 0; //(0 ->尚未取號) (1 -> 取號) (2 ->入座)

    private TextView mNmber;
    private TextView waiting;
    private Button mgetNumber;
    private TextView textView;
    private SeekBar seekBar;
    private String documentID;
    private String getNumberTime;
    private int waitingNum;
    private int wtNum;



    // 初始化 FirebaseFirestore 指定集合路徑

    private FirebaseFirestore db = FirebaseFirestore.getInstance();


    public static Fragment newInstance() {
        UserActivityReservationFragment_tb2 fragment = new UserActivityReservationFragment_tb2();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_reservation_fragment_tb2, null);
        handViews(view);
        waiting = view.findViewById(R.id.TextView_waiting);
        waiting.setText("目前現場候位組數:" + String.valueOf(waitingNumber()));
        getNumber(view);

        return view;
    }

    private void handViews(final View view) {
        textView = view.findViewById(R.id.TextView_several);
        seekBar = view.findViewById(R.id.seekBar);
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

            /* 當用戶手指離開SeekBar時，會自動呼叫onStopTrackingTouch()，
               以Toast顯示SeekBar的值 */
            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                Toast.makeText(view.getContext(),
                        "訂位人數 =" + seekBar.getProgress(),
                        Toast.LENGTH_SHORT).show();
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

    //顯示目前現場候位組數
    private int waitingNumber (){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN);
        String DateString = dateFormat.format(new Date());
        String collectionPatch = "smartOrder/waiting/" + DateString;
        CollectionReference citiesRef = db.collection(collectionPatch);

        Query query = citiesRef.whereEqualTo(STATUS_KEY, 1);

        query.get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
            @Override
            public void onComplete(@NonNull Task<QuerySnapshot> task) {
                if(task.isSuccessful()){
                    wtNum = task.getResult().getDocuments().size();
                }
            }
        });
        return wtNum;
    }

    //候位取號
    public void getNumber(View view) {

        mgetNumber = view.findViewById(R.id.bt_getNumber);
        mNmber = view.findViewById(R.id.number);

        mgetNumber.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN);
                String DateString = dateFormat.format(new Date());
                String collectionPatch = "smartOrder/waiting/" + DateString;
                final DocumentReference dr = db.collection(collectionPatch).document();


                status = 1; //1 -> 取號
                waitingNum = 0;
                waitingNum = waitingNumber()+1;
                Date date = new Date();
                Map<String, Object> dataToSave = new HashMap<String, Object>();
                dataToSave.put(NUMBER_KEY, waitingNum);
                dataToSave.put(STATUS_KEY, status);
                dataToSave.put(NAME_KEY, userName);
                dataToSave.put(SEVERAL_KEY,seekBar.getProgress());
                dataToSave.put(TIME_KEY, FieldValue.serverTimestamp());
                dr.set(dataToSave).addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        documentID = dr.getId();
                        Log.d(TAG, "檔案已儲存" + getNumberTime);
                        getTimestamp ();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Log.d(TAG, "儲存失敗", e);
                    }
                });

                //即時監聽順位
                db.collection(collectionPatch)
                        .whereEqualTo(STATUS_KEY, 1)
                        .whereLessThanOrEqualTo(NUMBER_KEY,waitingNum)
                        .addSnapshotListener(new EventListener<QuerySnapshot>() {
                            @Override
                            public void onEvent(@Nullable QuerySnapshot value,
                                                @Nullable FirebaseFirestoreException e) {
                                if (e != null) {
                                    Log.w(TAG, "監聽失敗.", e);
                                    return;
                                }
                                waiting.setText("您的等候順位為:");
                                mNmber.setText(String.valueOf(value.size()));
                            }
                        });

            }
        });
    }

    private void getTimestamp (){
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.TAIWAN);
        String DateString = dateFormat.format(new Date());
        String collectionPatch = "smartOrder/waiting/" + DateString;
        DocumentReference docRef = db.document(collectionPatch + "/" + documentID);
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        Log.d(TAG, "DocumentSnapshot data: " + document.getData());
                    } else {
                        Log.d(TAG, "No such document");
                    }
                } else {
                    Log.d(TAG, "get failed with ", task.getException());
                }
            }
        });
    }
}