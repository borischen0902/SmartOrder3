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

import com.facebook.share.model.ShareHashtag;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class UserActivityCouponFragment extends Fragment {
    private RecyclerView rvCoupon;
    private static final String TAG = "ShowCoupon";
    CCommonTask showCouponTask, receiveCouponQtyTask;
    ImageTask couponImageTask;
    CouponAdapter couponAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.user_coupon_fragment, container, false);
        rvCoupon = (RecyclerView) view.findViewById(R.id.rvCoupon);
        rvCoupon.setLayoutManager(new LinearLayoutManager(getActivity()));
        couponAdapter = new CouponAdapter(inflater, getCoupon());
        rvCoupon.setAdapter(couponAdapter);
        return view;
    }

    /* 取得資料 */
    private List<CCoupon> getCoupon() {
        List<CCoupon> coupons = new ArrayList<>();
        if (CCommon.isNetworkConnected(getActivity())) {
            String url = CCommon.URL + "/SmartOrderServlet";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "showCoupon");
            String jsonOut = jsonObject.toString();
            showCouponTask = new CCommonTask(url, jsonOut);
            try {
                String jsonIn = showCouponTask.execute().get();
                Type listType = new TypeToken<List<CCoupon>>() {
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
    private class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.MyViewHolder> {
        private LayoutInflater inflater;
        private List<CCoupon> coupon;
        private int isCardViewExtend = -1;
        private int newCouponQty, couponQty;

        public CouponAdapter(LayoutInflater inflater, List<CCoupon> coupon) {
            this.inflater = inflater;
            this.coupon = coupon;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivCoupon;
            TextView tvCouponTitle, tvMore;
            LinearLayout llExtend;
            TextView tvCouponInfoDetail, tvCouponQty;
            Button btCouponReceive;
            ShareButton btCouponShare;
            CardView cvCoupon;

            public MyViewHolder(View coupon_item) {
                super(coupon_item);
                ivCoupon = coupon_item.findViewById(R.id.ivCoupon);
                tvCouponTitle = coupon_item.findViewById(R.id.tvCouponTitle);
                tvMore = coupon_item.findViewById(R.id.tvMore);
                llExtend = coupon_item.findViewById(R.id.llExtend);
                tvCouponInfoDetail = coupon_item.findViewById(R.id.tvCouponInfoDetail);
                tvCouponQty = coupon_item.findViewById(R.id.tvCouponQty);
                btCouponReceive = coupon_item.findViewById(R.id.btCouponReceive);
                btCouponShare = coupon_item.findViewById(R.id.btCouponShare);
                cvCoupon = coupon_item.findViewById(R.id.cvCoupon);
                llExtend.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
            if (coupon != null) {
                return coupon.size();
            } else {
                return 0;
            }
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View coupon_item = inflater.inflate(R.layout.user_coupon_fragment_item, viewGroup, false);
            return new MyViewHolder(coupon_item);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int position) {
            final CCoupon couponItem = coupon.get(position);
            final int id = couponItem.getId_coupon_content();
            couponQty = couponItem.getQty();
            getCouponImage(id, myViewHolder);
            myViewHolder.tvCouponTitle.setText(couponItem.getTitle());
            myViewHolder.tvCouponQty.setText("剩餘 " + couponQty + " 張");
            myViewHolder.tvMore.setOnClickListener(new View.OnClickListener() { //  開啟卡片延伸
                @Override
                public void onClick(View v) {
                    getIsCardViewExtend(myViewHolder.itemView, position);
                }
            });

            /* 判斷此item是否要開啟卡片 */
            if (isCardViewExtend == position) {
                myViewHolder.llExtend.setVisibility(View.VISIBLE);
                myViewHolder.tvCouponInfoDetail.setText(couponItem.getText());
                myViewHolder.btCouponReceive.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {   // 領取優惠券
                        newCouponQty = receiveCoupon(id, couponQty);
                        if ((couponQty == 0) && (newCouponQty == 0)) {
                            couponItem.setQty(newCouponQty);
                            couponAdapter.notifyDataSetChanged();
                            Toast.makeText(v.getContext(), "優惠券已領取完畢", Toast.LENGTH_SHORT).show();
                        } else if (newCouponQty >= 0) {
                            couponItem.setQty(newCouponQty);
                            couponAdapter.notifyDataSetChanged();
                            Toast.makeText(v.getContext(), "您已領取此優惠券", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(v.getContext(), "您已領取過此優惠券", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
                ShareLinkContent content = new ShareLinkContent.Builder()
                        .setShareHashtag(new ShareHashtag.Builder().setHashtag("#快使用SmartOrderAPP" + couponItem.getText()).build())
                        .build();
                myViewHolder.btCouponShare.setShareContent(content);
            } else {
                myViewHolder.llExtend.setVisibility(View.GONE);
            }

        }

        /* 領取優惠券 */
        private int receiveCoupon(int id, int couponQty) {
            int updateCouponQty = couponQty;
            if (CCommon.isNetworkConnected(getActivity())) {
                String url = CCommon.URL + "/SmartOrderServlet";
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "receiveCoupon");
                SharedPreferences pref = getActivity().getSharedPreferences(CCommon.LOGIN_INFO, MODE_PRIVATE);  // 取得帳號
                jsonObject.addProperty("account", pref.getString("account", ""));
                jsonObject.addProperty("id_coupon_content", id);
                String jsonOut = jsonObject.toString();
                receiveCouponQtyTask = new CCommonTask(url, jsonOut);
                try {
                    String jsonIn = receiveCouponQtyTask.execute().get();
                    jsonObject = new Gson().fromJson(jsonIn, JsonObject.class);
                    updateCouponQty = jsonObject.get("newCouponQty").getAsInt();
                } catch (Exception e) {
                    Log.e(TAG, e.toString());
                }
            } else {
                Toast.makeText(getActivity(), "未連線", Toast.LENGTH_SHORT).show();
            }
            return updateCouponQty;
        }

        /* 取得優惠券圖 */
        private void getCouponImage(int id, MyViewHolder myViewHolder) {
            String url = CCommon.URL + "/SmartOrderServlet";
            couponImageTask = new ImageTask(url, id, myViewHolder.ivCoupon);
            couponImageTask.execute();
        }

        /* 卡片延伸 */
        private void getIsCardViewExtend(View view, int position) {
            if (isCardViewExtend == position) { //  收卡片
                isCardViewExtend = -1;
                couponAdapter.notifyDataSetChanged();
            } else {    //  展開卡片
                int preIsCardViewExtend = isCardViewExtend;
                isCardViewExtend = position;
                if ((isCardViewExtend < preIsCardViewExtend) || (preIsCardViewExtend == -1)) {
                    couponAdapter.notifyDataSetChanged();
                    moveTo(view);
                } else if (isCardViewExtend > preIsCardViewExtend) {
                    couponAdapter.notifyDataSetChanged();
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

    @Override
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
