package com.example.boris.smartorder3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class UserActivityCouponFragment extends Fragment {
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_coupon_fragment, container, false);
        RecyclerView rvCoupon = (RecyclerView) view.findViewById(R.id.rvCoupon);
        rvCoupon.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvCoupon.setAdapter(new CouponAdapter(inflater, getCoupon()));

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

        public CouponAdapter(LayoutInflater inflater, List<CCoupon> coupon) {
            this.inflater = inflater;
            this.coupon = coupon;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView ivCoupon;
            TextView tvCouponTitle;

            public MyViewHolder(View coupon_item) {
                super(coupon_item);
                ivCoupon = coupon_item.findViewById(R.id.ivCoupon);
                tvCouponTitle = coupon_item.findViewById(R.id.tvCouponTitle);
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
            myViewHolder.ivCoupon.setImageResource(couponItem.getPicture());
            myViewHolder.tvCouponTitle.setText(couponItem.getTitle());
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Bundle bundle = new Bundle();
                    bundle.putSerializable("coupon", couponItem);
                    UserActivityCouponDetailFragment couponDetail = new UserActivityCouponDetailFragment();
                    couponDetail.setArguments(bundle);
                    changeFragment(couponDetail);
                }
            });
        }

        //進入Detail頁面(Fragment)
        private void changeFragment(Fragment fragment) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction =
                    fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.content, fragment);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        }

    }
}
