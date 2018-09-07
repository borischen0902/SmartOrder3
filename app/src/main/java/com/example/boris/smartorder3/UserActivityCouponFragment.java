package com.example.boris.smartorder3;

import android.os.Bundle;
import android.support.annotation.NonNull;
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

public class UserActivityCouponFragment extends Fragment {
    private CouponAdapter couponAdapter;
    RecyclerView rvCoupon;

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

    //假資料
    private List<CCoupon> getCoupon() {
        List<CCoupon> coupon = new ArrayList<>();
        coupon.add(new CCoupon(R.drawable.info1, 10, "生日優惠", "生日優惠, 打九折"));
        coupon.add(new CCoupon(R.drawable.info2, 9, "VIP優惠", "VIP打八折"));
        coupon.add(new CCoupon(R.drawable.info3, 8, "94要打折", "打到骨折"));
        coupon.add(new CCoupon(R.drawable.info4, 7, "結束營業優惠", "2/30消費免錢!"));
        return coupon;
    }

    //把Data binding在View
    private class CouponAdapter extends RecyclerView.Adapter<CouponAdapter.MyViewHolder> {
        private LayoutInflater inflater;
        private List<CCoupon> coupon;
        private int isCardViewExtend = -1;

        public CouponAdapter(LayoutInflater inflater, List<CCoupon> coupon) {
            this.inflater = inflater;
            this.coupon = coupon;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivCoupon;
            TextView tvCouponTitle, tvMore;
            LinearLayout llExtend;
            TextView tvCouponInfoDetail, tvCouponQty;
            Button btCouponReceive, btCouponShare;
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
            return coupon.size();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View coupon_item = inflater.inflate(R.layout.user_coupon_fragment_item, viewGroup, false);
            return new MyViewHolder(coupon_item);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
            final CCoupon couponItem = coupon.get(i);
            final int position = i;
            myViewHolder.ivCoupon.setImageResource(couponItem.getPicture());
            myViewHolder.tvCouponTitle.setText(couponItem.getTitle());
            myViewHolder.tvMore.setOnClickListener(new View.OnClickListener() { //  開啟卡片延伸
                @Override
                public void onClick(View v) {
                    getIsCardViewExtend(position);
                }
            });
            if (isCardViewExtend == position) {
                myViewHolder.llExtend.setVisibility(View.VISIBLE);
                moveTo(myViewHolder.itemView);
                myViewHolder.tvCouponInfoDetail.setText(couponItem.getInfo());
                myViewHolder.btCouponReceive.setOnClickListener(new View.OnClickListener() {
                    private int couponQty = couponItem.getQty();

                    @Override
                    public void onClick(View v) {
                        if (couponQty > 0) {
                            couponQty -= 1;
                            Toast.makeText(v.getContext(), "已領取優惠券", Toast.LENGTH_SHORT).show();
                            myViewHolder.tvCouponQty.setText("剩餘 " + couponQty + " 張");
                        } else {
                            Toast.makeText(v.getContext(), "優惠券已領取完畢", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
                myViewHolder.btCouponShare.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(v.getContext(), "對不起, 您沒有朋友", Toast.LENGTH_SHORT).show();
                    }
                });
            } else {
                myViewHolder.llExtend.setVisibility(View.GONE);
            }
        }

        /* 卡片延伸 */
        private void getIsCardViewExtend(int position) {
            if (isCardViewExtend == position) {
                isCardViewExtend = -1;
                notifyItemChanged(position);
            } else {
                int preIsCardViewExtend = isCardViewExtend;
                isCardViewExtend = position;
                notifyItemChanged(preIsCardViewExtend);
                notifyItemChanged(isCardViewExtend);
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


}
