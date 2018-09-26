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
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;
import com.google.firebase.firestore.MetadataChanges;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class WaiterActivityOrderFragment extends Fragment {
    private List<CShowOrderList> order = new ArrayList<>();
    private OrderAdapter orderAdapter;
    private CCommonTask showOrderTask, changeOrderStatusTask;
    private static final String TAG = "ShowOrder";
    public RecyclerView rvOrder;

    //菜單更新前置
    public static final String TIME_KEY = "time"; //更新產生新的時間戳
    private ListenerRegistration listenTable; //listenTable.remove(); 關閉監聽器用
    private FirebaseFirestore db = FirebaseFirestore.getInstance();// 初始化 FirebaseFirestore
    private String documentPatch = "/smartOrder/update";//指定檔案路徑

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.waiter_order_fragment, container, false);
        rvOrder = (RecyclerView) view.findViewById(R.id.rvOrder);
        rvOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        updateListener();//菜單更新監聽器
        order = getOrder();
        orderAdapter = new OrderAdapter(inflater, order);
        rvOrder.setAdapter(orderAdapter);
        ItemTouchHelper.Callback callback = new SwipeCardCallBack(order, orderAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rvOrder);

        return view;
    }

    /* 取得訂單 */
    private List<CShowOrderList> getOrder() {
        List<CShowOrderList> data = new ArrayList<>();
        //if (CCommon.isNetworkConnected(getActivity())) {
        String url = CCommon.URL + "/SmartOrderServlet";
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "showOrder");
        String jsonOut = jsonObject.toString();
        showOrderTask = new CCommonTask(url, jsonOut);
        try {
            String jsonIn = showOrderTask.execute().get();
            Type listType = new TypeToken<List<CShowOrderList>>() {
            }.getType();
            data = new Gson().fromJson(jsonIn, listType);
        } catch (Exception e) {
            Log.e(TAG, e.toString());
        }
        //} else {
        //   Toast.makeText(getActivity(), "未連線", Toast.LENGTH_SHORT).show();
        //}
        return data;
    }

    //把Data binding在View
    private class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.MyViewHolder> {
        private LayoutInflater inflater;
        private List<CShowOrderList> order;

        public OrderAdapter(LayoutInflater inflater, List<CShowOrderList> order) {

            this.inflater = inflater;
            this.order = order;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvTableID, tvItem1, tvItem2, tvItem3, tvItem4, tvItem5;
            CardView cvOrderInfo;
            LinearLayout llForRamen;

            public MyViewHolder(View order_item) {
                super(order_item);
                tvTableID = order_item.findViewById(R.id.tvTableID);
                cvOrderInfo = order_item.findViewById(R.id.cvOrderInfo);
                tvItem1 = order_item.findViewById(R.id.tvItem1);
                llForRamen = order_item.findViewById(R.id.llForRamen);
                tvItem2 = order_item.findViewById(R.id.tvItem2);
                tvItem3 = order_item.findViewById(R.id.tvItem3);
                tvItem4 = order_item.findViewById(R.id.tvItem4);
                tvItem5 = order_item.findViewById(R.id.tvItem5);
                llForRamen.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            if (order != null) {
                for (int i = 0; i < order.size(); i++) {
                    if (order.get(i).getStatus() == 1) {
                        order.remove(i);
                    }
                }
                return order.size();
            } else {
                return 0;
            }

        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View order_item = inflater.inflate(R.layout.waiter_order_fragment_item, viewGroup, false);
            return new MyViewHolder(order_item);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
            final CShowOrderList orderItem = order.get(i);
            myViewHolder.tvTableID.setText("桌號 : " + Integer.toString(orderItem.getId_table()));
            String item = orderItem.getItem();
            switch (item) {
                case "拉麵":
                    myViewHolder.cvOrderInfo.setCardBackgroundColor(Color.rgb(114, 150, 110));
                    myViewHolder.llForRamen.setVisibility(View.VISIBLE);
                    decodeFlavor(myViewHolder, orderItem.getFlavor());
                    break;
                default:
                    myViewHolder.cvOrderInfo.setCardBackgroundColor(Color.rgb(255, 255, 255));
                    myViewHolder.llForRamen.setVisibility(View.GONE);
                    myViewHolder.tvItem1.setText(orderItem.getItem());
                    break;
            }
        }

        /* 拉麵口味解碼 */
        private void decodeFlavor(MyViewHolder myViewHolder, String flavor) {
            for (int i = 0; i < flavor.length(); i++) {
                switch (i) {
                    case 0: // 濃度
                        switch (flavor.charAt(0)) {
                            case '0':
                                myViewHolder.tvItem1.setText("淡");
                                break;
                            case '1':
                                myViewHolder.tvItem1.setText("中");
                                break;
                            case '2':
                                myViewHolder.tvItem1.setText("濃");
                                break;
                            default:
                                break;
                        }
                        break;
                    case 1: //油量
                        switch (flavor.charAt(1)) {
                            case '0':
                                myViewHolder.tvItem2.setText("淡");
                                break;
                            case '1':
                                myViewHolder.tvItem2.setText("中");
                                break;
                            case '2':
                                myViewHolder.tvItem2.setText("濃");
                                break;
                            default:
                                break;
                        }
                        break;
                    case 2: //蒜量
                        switch (flavor.charAt(2)) {
                            case '0':
                                myViewHolder.tvItem3.setText("淡");
                                break;
                            case '1':
                                myViewHolder.tvItem3.setText("中");
                                break;
                            case '2':
                                myViewHolder.tvItem3.setText("濃");
                                break;
                            default:
                                break;
                        }
                        break;
                    case 3: //辣度
                        switch (flavor.charAt(3)) {
                            case '0':
                                myViewHolder.tvItem4.setText("小辣");
                                break;
                            case '1':
                                myViewHolder.tvItem4.setText("中辣");
                                break;
                            case '2':
                                myViewHolder.tvItem4.setText("大辣");
                                break;
                            default:
                                break;
                        }
                        break;
                    case 4: //硬度
                        switch (flavor.charAt(4)) {
                            case '0':
                                myViewHolder.tvItem5.setText("軟");
                                break;
                            case '1':
                                myViewHolder.tvItem5.setText("普通");
                                break;
                            case '2':
                                myViewHolder.tvItem5.setText("硬");
                                break;
                            default:
                                break;
                        }
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /* 滑動刪除 */
    private class SwipeCardCallBack extends ItemTouchHelper.SimpleCallback {
        private List<CShowOrderList> orders;
        private OrderAdapter orderAdapter;

        public SwipeCardCallBack(List<CShowOrderList> order, OrderAdapter orderAdapter) {
            super(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.orders = order;
            this.orderAdapter = orderAdapter;
        }

        public SwipeCardCallBack(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        public SwipeCardCallBack() {
            super(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.UP |
                            ItemTouchHelper.RIGHT | ItemTouchHelper.DOWN
            );
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            orders.get(viewHolder.getLayoutPosition()).setStatus(1);
            orderAdapter.notifyDataSetChanged();
            changeOrderStatus(orders.get(viewHolder.getLayoutPosition()).getId_order_detail());
            orderAdapter.notifyItemRemoved(viewHolder.getLayoutPosition());
        }

        /* 更改餐點狀態 */
        private void changeOrderStatus(int id_order_detail) {
            if (CCommon.isNetworkConnected(getActivity())) {
                String url = CCommon.URL + "/SmartOrderServlet";
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "changeOrderStatus");
                jsonObject.addProperty("id", id_order_detail);
                String jsonOut = jsonObject.toString();
                changeOrderStatusTask = new CCommonTask(url, jsonOut);
                try {
                    String jsonIn = changeOrderStatusTask.execute().get();
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            } else {
                Toast.makeText(getActivity(), "未連線", Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (showOrderTask != null) {
            showOrderTask.cancel(true);
            showOrderTask = null;
        }

        if (changeOrderStatusTask != null) {
            changeOrderStatusTask.cancel(true);
            changeOrderStatusTask = null;
        }
    }

    //餐單更新監聽器
    private void updateListener() {
        DocumentReference docRef = db.document(documentPatch);
        docRef.addSnapshotListener(MetadataChanges.INCLUDE, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot snapshot,
                                @Nullable FirebaseFirestoreException e) {
                if (e != null) {
                    Log.w(TAG, "檔案讀取失敗:", e);
                    return;
                }

                if (snapshot.getTimestamp(TIME_KEY) != null) {
                    //Log.d(TAG, "更新時間:" + snapshot.getTimestamp(TIME_KEY));
                    updateRecyclerView();
                }
            }
        });
    }

    /* RecyclerView更新 */
    private void updateRecyclerView() {
        order.clear();
        List<CShowOrderList> newOrder = getOrder();
        order.addAll(newOrder);
        orderAdapter.notifyDataSetChanged();
    }

}
