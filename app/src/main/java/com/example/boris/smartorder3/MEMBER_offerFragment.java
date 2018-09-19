package com.example.boris.smartorder3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MEMBER_offerFragment extends Fragment {
    private OfferAdapter offerAdapter;
    private RecyclerView rvCoupon;

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
        offerAdapter = new MEMBER_offerFragment.OfferAdapter(inflater, getCoupon());
        rvCoupon.setAdapter(offerAdapter);
        return view;
    }

    //假資料
    private List<OfferCoupon> getCoupon() {
        List<OfferCoupon> coupon = new ArrayList<>();
        coupon.add(new OfferCoupon(R.drawable.info1, 10, "生日優惠", "生日優惠, 打九折"));
        coupon.add(new OfferCoupon(R.drawable.info2, 9, "VIP優惠", "VIP打八折"));
        coupon.add(new OfferCoupon(R.drawable.info3, 8, "94要打折", "打到骨折"));
        coupon.add(new OfferCoupon(R.drawable.info4, 7, "結束營業優惠", "2/30消費免錢!"));
        return coupon;
    }

    //把Data binding在View
    private class OfferAdapter extends RecyclerView.Adapter<OfferAdapter.MyViewHolder> {
        private LayoutInflater inflater;
        private List<OfferCoupon> offercoupon;
        private int isCardViewExtend = -1;

        public OfferAdapter(LayoutInflater inflater, List<OfferCoupon> coupon) {
            this.inflater = inflater;
            this.offercoupon = coupon;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivCoupon;
            TextView tvCouponTitle;
            LinearLayout llExtend;
            TextView tvCouponInfoDetail, tvCouponQty;
            Button btCouponuse, btCouponShare;
            CardView cvCoupon;

            public MyViewHolder(View coupon_item) {
                super(coupon_item);
                ivCoupon = coupon_item.findViewById(R.id.ivofferCoupon);
                tvCouponTitle = coupon_item.findViewById(R.id.tvofferCouponTitle);
                llExtend = coupon_item.findViewById(R.id.llofferExtend);
                tvCouponInfoDetail = coupon_item.findViewById(R.id.tvofferCouponInfoDetail);
                tvCouponQty = coupon_item.findViewById(R.id.tvofferCouponQty);
                cvCoupon = coupon_item.findViewById(R.id.cvofferCoupon);
                llExtend.setVisibility(View.GONE);
            }
        }

        @Override
        public int getItemCount() {
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
            myViewHolder.tvCouponQty.setText("剩餘 " + couponItem.getQty() + " 張");
            myViewHolder.ivCoupon.setImageResource(couponItem.getPicture());
            myViewHolder.tvCouponTitle.setText(couponItem.getTitle());
            myViewHolder.llExtend.setVisibility(View.VISIBLE);
            moveTo(myViewHolder.itemView);
            myViewHolder.tvCouponInfoDetail.setText(couponItem.getInfo());
            myViewHolder.btCouponuse.setOnClickListener(new View.OnClickListener() {
                //                private int couponQty = couponItem.getQty();
                @Override
                public void onClick(View v) {
                    if (couponItem.getQty() > 0) {
                        couponItem.setQty(couponItem.getQty() - 1);
                        Toast.makeText(v.getContext(), "已使用優惠券", Toast.LENGTH_SHORT).show();
                        myViewHolder.tvCouponQty.setText("剩餘 " + couponItem.getQty() + " 張");
                    } else {
                        Toast.makeText(v.getContext(), "已無優惠券", Toast.LENGTH_SHORT).show();
                    }

                }
            });
            myViewHolder.btCouponShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(v.getContext(), "對不起, 您沒有朋友", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


    /* 點擊時調整item位置 */
    private void moveTo(View view) {
        int itemHeight = view.getHeight();
        int screenHeight = getResources().getDisplayMetrics().heightPixels;
        int scrollHeight = view.getTop() - (screenHeight / 2 - itemHeight / 2);
        rvCoupon.smoothScrollBy(0, scrollHeight);
    }

}



