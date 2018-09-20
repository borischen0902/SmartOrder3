package com.example.boris.smartorder3;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class WaiterActivityStatusFragment extends Fragment {
    private List<CStatus> status = new ArrayList<>();
    private StatusAdapter statusAdapter;

    //座位監聽前置
    public static final String STATUS_KEY = "status"; //等候狀態 ０：空桌  １：有人
    public static final String TAG = "FirebaseDeBug"; //Log 用
    private ListenerRegistration listenTable; //listenTable.remove(); 關閉監聽器用
    private List<Long> tables = new ArrayList<>();//用來存放有人的桌號


    // 初始化 FirebaseFirestore 指定集合路徑
    private FirebaseFirestore db = FirebaseFirestore.getInstance();
    private String collectionPatch = "smartOrder/waiting/table";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.waiter_status_fragment, container, false);
        RecyclerView rvStatus = (RecyclerView) view.findViewById(R.id.rvStatus);
        rvStatus.setLayoutManager(new LinearLayoutManager(getActivity()));
        status = getStatus();
        statusAdapter = new StatusAdapter(inflater, status);
        rvStatus.setAdapter(statusAdapter);
        ItemTouchHelper.Callback callback = new SwipeCardCallBack(status, statusAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rvStatus);
        tableListener(); //啟用座位狀態監聽器
        cleanTable(1); //測試用 輸入桌號 改變狀態
        return view;
    }

    private List<CStatus> getStatus() {
        status.add(new CStatus(2, "11:02", "12:00", 0));
        status.add(new CStatus(1, "11:05", "12:02", 1));
        status.add(new CStatus(3, "11:06", "12:03", 0));
        status.add(new CStatus(4, "11:08", "12:04", 0));
        return status;
    }

    private class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.MyViewHolder> {
        private LayoutInflater inflater;
        private List<CStatus> status;

        public StatusAdapter(LayoutInflater inflater, List<CStatus> status) {
            this.inflater = inflater;
            this.status = status;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvTableID;

            public MyViewHolder(View itemView) {
                super(itemView);
                tvTableID = itemView.findViewById(R.id.tvTableID);
            }
        }

        @Override
        public int getItemCount() {
            for (int i = 0; i < status.size(); i++) {
                if (status.get(i).getCurrentStatus() == 1) {
                    status.remove(i);
                }
            }
            return status.size();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View status_item = inflater.inflate(R.layout.waiter_status_fragment_item, parent, false);
            return new MyViewHolder(status_item);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            CStatus statusItem = status.get(position);
            holder.tvTableID.setText(Integer.toString(statusItem.getTableID()));

        }

    }

    private class SwipeCardCallBack extends ItemTouchHelper.SimpleCallback {
        private List<CStatus> status;
        private StatusAdapter statusAdapter;

        public SwipeCardCallBack(List<CStatus> status, StatusAdapter statusAdapter) {
            super(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.status = status;
            this.statusAdapter = statusAdapter;
        }

        public SwipeCardCallBack(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            CStatus statusItem = status.get(viewHolder.getLayoutPosition());
            statusItem.setCurrentStatus(1);
            statusAdapter.notifyDataSetChanged();
        }
    }

    //座位狀態監聽器
    private void tableListener(){

        Query query = db.collection(collectionPatch).whereEqualTo(STATUS_KEY,1);
        listenTable = query.addSnapshotListener(MetadataChanges.INCLUDE,new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "檔案讀取失敗:", e);
                    return;
                }

                tables.removeAll(tables);
                for (QueryDocumentSnapshot doc : value) {
                    if (doc.get(STATUS_KEY) != null) {
                        tables.add(doc.getLong("table"));
                    }
                }
                Log.d(TAG, "目前有人的桌號: " + tables);

            }
        });

    }

    //清潔完桌面設定狀態為０ 表示空桌 傳入桌號
    private void cleanTable(int table){
        db.collection(collectionPatch).document(String.valueOf(table))
                .update(STATUS_KEY, 0)
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
}
