package com.example.boris.smartorder3;


import android.graphics.drawable.Drawable;
import android.graphics.drawable.Icon;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public class UserActivityMemberFragment extends Fragment {
    public static final String TAG = "TabActivity";
    public static final String []sTitle = new String[]{"會員資訊","詳細訂單","收藏優惠券"};


    private TabLayout mTabLayout;
    private ViewPager mViewPager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_fragment, container, false);
        handleview(view);
        return view;

    }

    private void handleview(View view) {


        mViewPager = view.findViewById(R.id.view_pager);
        mTabLayout = view.findViewById(R.id.mambertab_layout);
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[0]).setIcon(R.drawable.information_24dp));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[1]).setIcon(R.drawable.orderlist));
        mTabLayout.addTab(mTabLayout.newTab().setText(sTitle[2]).setIcon(R.drawable.offer));

        mTabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Log.i(TAG,"onTabSelected:"+tab.getText());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        mTabLayout.setupWithViewPager(mViewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(MEMBER_informationFragment.newInstance());
        fragments.add(MEMBER_orderlistFragment.newInstance());
        fragments.add(MEMBER_offerFragment.newInstance());

        MemberFragmentAdapter adapter = new MemberFragmentAdapter(getChildFragmentManager(),fragments, Arrays.asList(sTitle));
        mViewPager.setAdapter(adapter);
        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.i(TAG,"select page:"+position);

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

    }
}












