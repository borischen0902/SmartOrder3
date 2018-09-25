package com.example.boris.smartorder3;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
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
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class WaiterActivityStatusFragment extends Fragment {
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
        tableListener(); //啟用座位狀態監聽器
        statusAdapter = new StatusAdapter(inflater, tables);
        rvStatus.setAdapter(statusAdapter);
        ItemTouchHelper.Callback callback = new SwipeCardCallBack(tables, statusAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rvStatus);
        return view;
    }

    private class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.MyViewHolder> {
        private LayoutInflater inflater;
        private List<Long> tables;

        public StatusAdapter(LayoutInflater inflater, List<Long> tables) {
            this.inflater = inflater;
            this.tables = tables;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvTableID;
            CardView cvStatus;

            public MyViewHolder(View itemView) {
                super(itemView);
                tvTableID = itemView.findViewById(R.id.tvTableID);
                cvStatus = itemView.findViewById(R.id.cvStatus);
            }
        }

        @Override
        public int getItemCount() {
            return tables.size();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View status_item = inflater.inflate(R.layout.waiter_status_fragment_item, parent, false);
            return new MyViewHolder(status_item);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            Long table = tables.get(position);
            holder.tvTableID.setText("第 " + Long.toString(table) + " 桌                                 用餐中");
            holder.cvStatus.setCardBackgroundColor(Color.rgb(251, 226, 82));
        }

    }

    private class SwipeCardCallBack extends ItemTouchHelper.SimpleCallback {
        private List<Long> tables;
        private StatusAdapter statusAdapter;

        public SwipeCardCallBack(List<Long> tables, StatusAdapter statusAdapter) {
            super(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.tables = tables;
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
            int table = Integer.parseInt(String.valueOf(tables.get(viewHolder.getLayoutPosition())));
            cleanTable(table);
            statusAdapter.notifyDataSetChanged();
        }
    }

    //座位狀態監聽器
    private void tableListener() {
        Query query = db.collection(collectionPatch).whereEqualTo(STATUS_KEY, 1);
        listenTable = query.addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    //   Log.w(TAG, "檔案讀取失敗:", e);
                    return;
                }

                tables.removeAll(tables);
                for (QueryDocumentSnapshot doc : value) {
                    if (doc.get(STATUS_KEY) != null) {
                        tables.add(doc.getLong("table"));
                    }
                }
                statusAdapter.notifyDataSetChanged();
                //Log.d(TAG, "目前有人的桌號: " + tables);

            }
        });

    }

    //清潔完桌面設定狀態為０ 表示空桌 傳入桌號
    private void cleanTable(int table) {
        db.collection(collectionPatch).document(String.valueOf(table))
                .update(STATUS_KEY, 0)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {
                        //       Log.d(TAG, "座位狀態更新成功!");
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        //       Log.w(TAG, "座位狀態更新失敗", e);
                    }
                });
    }
}
