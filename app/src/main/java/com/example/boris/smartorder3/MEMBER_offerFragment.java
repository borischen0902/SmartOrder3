package com.example.boris.smartorder3;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
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

public class MEMBER_offerFragment extends Fragment {
    private OfferAdapter offerAdapter;
    private RecyclerView rvCoupon;
    CCommonTask showCouponTask, receiveCouponQtyTask;
    ImageTask couponImageTask;



    public static Fragment newInstance() {
        MEMBER_offerFragment fragment = new MEMBER_offerFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_offerfragment, container, false);
        rvCoupon = view.findViewById(R.id.reoffer);
        rvCoupon.setLayoutManager(new LinearLayoutManager(getActivity()));
        SharedPreferences pref = getActivity().getSharedPreferences(CCommon.LOGIN_INFO, MODE_PRIVATE);//取得設定檔資料
        offerAdapter = new MEMBER_offerFragment.OfferAdapter(inflater,getcoupon(pref.getString("account","")));
        rvCoupon.setAdapter(offerAdapter);
        return view;
    }

    public List<OfferCoupon> getcoupon(String pref) {
        List<OfferCoupon> coupons = new ArrayList<>();
        if (CCommon.isNetworkConnected(getActivity())) {
            String url = CCommon.URL + "/SmartOrderServlet";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "showOfferCoupon");
            jsonObject.addProperty("account", pref);
            String jsonOut = jsonObject.toString();
            showCouponTask = new CCommonTask(url, jsonOut);
            try {
                String jsonIn = showCouponTask.execute().get();
                Type listType = new TypeToken<List<OfferCoupon>>() {
                }.getType();
                coupons = new Gson().fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        } else {
            Toast.makeText(getActivity(), "未連線", Toast.LENGTH_SHORT).show();
        }
        return coupons;
    }


    //把Data binding在View
    public class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {
        private LayoutInflater inflater;
        private List<OfferCoupon> offercoupon;


        public OfferAdapter(LayoutInflater inflater, List<OfferCoupon> getcoupon) {
            this.inflater = inflater;
            this.offercoupon = getcoupon;
        }


        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivCoupon;
            TextView tvCouponTitle;
            LinearLayout llExtend;
            TextView tvCouponInfoDetail, tvCouponStart,tvCouponEnd;
            CardView cvCoupon;

            public MyViewHolder(View coupon_item) {
                super(coupon_item);
                ivCoupon = coupon_item.findViewById(R.id.ivofferCoupon);
                tvCouponTitle = coupon_item.findViewById(R.id.tvofferCouponTitle);
                llExtend = coupon_item.findViewById(R.id.llofferExtend);
                tvCouponInfoDetail = coupon_item.findViewById(R.id.tvofferCouponInfoDetail);
                tvCouponStart = coupon_item.findViewById(R.id.tvofferStart);
                tvCouponEnd=coupon_item.findViewById(R.id.tvofferdate);
                cvCoupon = coupon_item.findViewById(R.id.cvofferCoupon);
                llExtend.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            if(offercoupon==null){
                return 0;
            }
            return offercoupon.size();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View coupon_item = inflater.inflate(R.layout.member_show_offer, viewGroup, false);
            return new MyViewHolder(coupon_item);
        }





        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
            final OfferCoupon couponItem = offercoupon.get(i);
            final int id = couponItem.getId_coupon_content();
            getCouponImage(id, myViewHolder);
            myViewHolder.tvCouponStart.setText("開始日期: " + couponItem.getStartdate());
            myViewHolder.tvCouponEnd.setText("結束日期: "+couponItem.getEnddate());
            myViewHolder.tvCouponTitle.setText(couponItem.getTitle());
            myViewHolder.llExtend.setVisibility(View.VISIBLE);
            moveTo(myViewHolder.itemView);
            myViewHolder.tvCouponInfoDetail.setText(couponItem.getText());

        }

        private void getCouponImage(int id,MEMBER_offerFragment.OfferAdapter.MyViewHolder myViewHolder) {
            String url = CCommon.URL + "/SmartOrderServlet";
            couponImageTask = new ImageTask(url, id, myViewHolder.ivCoupon);
            couponImageTask.execute();
        }
    }

        /* 點擊時調整item位置 */
        private void moveTo(View view) {
            int itemHeight = view.getHeight();
            int screenHeight = getResources().getDisplayMetrics().heightPixels;
            int scrollHeight = view.getTop() - (screenHeight / 2 - itemHeight / 2);
            rvCoupon.smoothScrollBy(0, scrollHeight);
        }


        public void onStop() {
            super.onStop();
            if (showCouponTask != null) {
                showCouponTask.cancel(true);
                showCouponTask = null;
            }

            if (couponImageTask != null) {
                couponImageTask.cancel(true);
                couponImageTask = null;
            }

            if (receiveCouponQtyTask != null) {
                receiveCouponQtyTask.cancel(true);
                receiveCouponQtyTask = null;
            }

    }
}


