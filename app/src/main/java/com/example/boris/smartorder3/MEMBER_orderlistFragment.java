package com.example.boris.smartorder3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.boris.smartorder3.UserActivityReservationFragment.TAG;

public class MEMBER_orderlistFragment extends Fragment {
    private OrderlistAdapter orderlistAdapter;
    private RecyclerView rvCoupon;
    CCommonTask showOrderTask, receiveCouponQtyTask;

    public static Fragment newInstance() {
        MEMBER_orderlistFragment fragment = new MEMBER_orderlistFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_orderlistfragment, container, false);
        rvCoupon = view.findViewById(R.id.reorderlist);
        rvCoupon.setLayoutManager(new LinearLayoutManager(getActivity()));
        SharedPreferences pref = getActivity().getSharedPreferences(CCommon.LOGIN_INFO, MODE_PRIVATE);//取得設定檔資料
        orderlistAdapter = new MEMBER_orderlistFragment.OrderlistAdapter(inflater, getOrder(pref.getString("account", "")));
        rvCoupon.setAdapter(orderlistAdapter);
        return view;
    }


    //假資料
    private List<OOder> getOrder(String pref) {
        List<OOder> oders = new ArrayList<>();
        if (CCommon.isNetworkConnected(getActivity())) {
            String url = CCommon.URL + "/SmartOrderServlet";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "showorderlist");
            jsonObject.addProperty("account", pref);
            String jsonOut = jsonObject.toString();
            showOrderTask = new CCommonTask(url, jsonOut);
            try {
                String jsonIn = showOrderTask.execute().get();
                Type listType = new TypeToken<List<OOder>>() {
                }.getType();
                oders = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        } else {
            Toast.makeText(getActivity(), "未連線", Toast.LENGTH_SHORT).show();
        }
        return oders;
    }

    private class OrderlistAdapter extends RecyclerView.Adapter<OrderlistAdapter.MyViewHolder> {
        private LayoutInflater inflater;
        private List<OOder> order;
        private int isCardViewExtend = -1;

        public OrderlistAdapter(LayoutInflater inflater, List<OOder> order) {
            this.inflater = inflater;
            this.order = order;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView name, tvMore;
            LinearLayout llExtend;
            TextView tvCouponInfoDetail, tvtotal, tvprice, tvitem, tvdate;
            CardView cvCoupon;

            public MyViewHolder(View order_item) {
                super(order_item);

                tvdate = order_item.findViewById(R.id.tvTitle);
                tvMore = order_item.findViewById(R.id.tvordermore);
                llExtend = order_item.findViewById(R.id.llorderExtend);
                name = order_item.findViewById(R.id.tvInfoDetail);
                tvprice = order_item.findViewById(R.id.tvprice);
                tvtotal = order_item.findViewById(R.id.tvtotal);
                tvitem = order_item.findViewById(R.id.tvitem);
                cvCoupon = order_item.findViewById(R.id.cvCoupon);
                llExtend.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            return order.size();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View coupon_item = inflater.inflate(R.layout.member_show_orderlist, viewGroup, false);
            return new MyViewHolder(coupon_item);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
            final OOder orderitem = order.get(i);
            final int position = i;
            myViewHolder.tvdate.setText(orderitem.getDatetime());
            myViewHolder.tvMore.setOnClickListener(new View.OnClickListener() { //  開啟卡片延伸
                @Override
                public void onClick(View v) {
                    getIsCardViewExtend(myViewHolder.itemView, position);


                }
            });
            if (isCardViewExtend == position) {
                myViewHolder.llExtend.setVisibility(View.VISIBLE);
                myViewHolder.tvCouponInfoDetail.setText(orderitem.getName());
                myViewHolder.tvitem.setText(orderitem.getId_item());
                myViewHolder.tvprice.setText(orderitem.getPrice());
                myViewHolder.tvtotal.setText("total");
                moveTo(myViewHolder.itemView);


            } else {
                myViewHolder.llExtend.setVisibility(View.GONE);
            }
        }

        /* 卡片延伸 */
        private void getIsCardViewExtend(View view, int position) {
            if (isCardViewExtend == position) { //  收卡片
                isCardViewExtend = -1;
                orderlistAdapter.notifyDataSetChanged();
            } else {    //  展開卡片
                int preIsCardViewExtend = isCardViewExtend;
                isCardViewExtend = position;
                if ((isCardViewExtend < preIsCardViewExtend) || (preIsCardViewExtend == -1)) {
                    orderlistAdapter.notifyDataSetChanged();
                    moveTo(view);
                } else if (isCardViewExtend > preIsCardViewExtend) {
                    orderlistAdapter.notifyDataSetChanged();
                    moveTo2(view);
                }
            }
        }


        /* 點擊時調整item位置 */
        private void moveTo(View view) {
            int itemHeight = view.getHeight();
            int screenHeight = getResources().getDisplayMetrics().heightPixels;
            int scrollHeight = view.getTop() - (screenHeight / 2 - itemHeight / 2);
            rvCoupon.smoothScrollBy(0, scrollHeight);
        }

        private void moveTo2(View view) {
            int screenHeight = getResources().getDisplayMetrics().heightPixels;
            int scrollHeight = view.getTop() - (screenHeight / 2);
            rvCoupon.smoothScrollBy(0, scrollHeight);
        }

    }
    public void onStop() {
        super.onStop();
        if (showOrderTask != null) {
            showOrderTask.cancel(true);
            showOrderTask = null;
        }

        if (receiveCouponQtyTask != null) {
            receiveCouponQtyTask.cancel(true);
            receiveCouponQtyTask = null;
        }


    }
}


