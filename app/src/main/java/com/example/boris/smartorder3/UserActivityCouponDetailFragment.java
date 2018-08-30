package com.example.boris.smartorder3;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class UserActivityCouponDetailFragment extends Fragment {
    ImageView ivCouponDetail;
    TextView tvCouponInfoDetail, tvCouponQty;
    CCoupon coupon;
    int couponPicture, couponQty;
    String couponInfo;
    Button btCouponReceive;
    Button btCouponShare;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_coupon_fragment_item_detail, container, false);
        handleViews(view);
        getCouponInfo();
        ivCouponDetail.setImageResource(couponPicture);
        tvCouponInfoDetail.setText(couponInfo);
        tvCouponQty.setText("剩餘 " + couponQty + " 張");
        btCouponReceive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (couponQty > 0) {
                    couponQty -= 1;
                    Toast.makeText(v.getContext(), "已領取優惠券", Toast.LENGTH_SHORT).show();
                    tvCouponQty.setText("剩餘 " + couponQty + " 張");
                } else {
                    Toast.makeText(v.getContext(), "優惠券已領取完畢", Toast.LENGTH_SHORT).show();
                }
            }
        });
        btCouponShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(v.getContext(), "對不起, 您沒有朋友", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }

    private void handleViews(View view) {
        ivCouponDetail = view.findViewById(R.id.ivCouponDetail);
        tvCouponInfoDetail = view.findViewById(R.id.tvCouponInfoDetail);
        tvCouponQty = view.findViewById(R.id.tvCouponQty);
        btCouponReceive = view.findViewById(R.id.btCouponReceive);
        btCouponShare = view.findViewById(R.id.btCouponShare);
    }

    private void getCouponInfo() {
        Bundle bundle = getArguments();
        coupon = (CCoupon) bundle.getSerializable("coupon");
        couponPicture = coupon.getPicture();
        couponQty = coupon.getQty();
        couponInfo = coupon.getInfo();
    }

}
