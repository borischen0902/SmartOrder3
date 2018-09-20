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

public class MEMBER_orderlistFragment extends Fragment {
    private OrderlistAdapter orderlistAdapter;
    private RecyclerView rvCoupon;

    public static Fragment newInstance(){
        MEMBER_orderlistFragment fragment = new MEMBER_orderlistFragment();
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_orderlistfragment, container, false);
        rvCoupon = view.findViewById(R.id.reorderlist);
        rvCoupon.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderlistAdapter = new OrderlistAdapter(inflater, getOrder());
        rvCoupon.setAdapter(orderlistAdapter);
        return view;
    }

    //假資料
    private List<OOder> getOrder() {
        List<OOder> order = new ArrayList<>();
        order.add(new OOder("2018/08/29", "醬油拉麵"));
        order.add(new OOder("2018/08/30", "屯古拉麵"));
        return order;
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

            TextView tvCouponTitle, tvMore;
            LinearLayout llExtend;
            TextView tvCouponInfoDetail, tvCouponQty;
            CardView cvCoupon;

            public MyViewHolder(View coupon_item) {
                super(coupon_item);

                tvCouponTitle = coupon_item.findViewById(R.id.tvCouponTitle);
                tvMore = coupon_item.findViewById(R.id.tvMore);
                llExtend = coupon_item.findViewById(R.id.llExtend);
                tvCouponInfoDetail = coupon_item.findViewById(R.id.tvCouponInfoDetail);
                tvCouponQty = coupon_item.findViewById(R.id.tvCouponQty);
                cvCoupon = coupon_item.findViewById(R.id.cvCoupon);
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
            myViewHolder.tvCouponTitle.setText(orderitem.getTitle());
            myViewHolder.tvMore.setOnClickListener(new View.OnClickListener() { //  開啟卡片延伸
                @Override
                public void onClick(View v) {
                    getIsCardViewExtend(position);
                }
            });
            if (isCardViewExtend == position) {
                myViewHolder.llExtend.setVisibility(View.VISIBLE);
                moveTo(myViewHolder.itemView);
                myViewHolder.tvCouponInfoDetail.setText(orderitem.getInfo());

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
