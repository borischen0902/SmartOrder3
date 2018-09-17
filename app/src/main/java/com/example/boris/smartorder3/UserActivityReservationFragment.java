package com.example.boris.smartorder3;

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


public class UserActivityReservationFragment extends Fragment {

    public static final String TAG = "TabActivity";
    public static final String []sTitle = new String[]{"訂位","候位"};
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_reservation_fragment, container, false);
        initView(view);
        return view;
    }

    private void initView(View view) {
        viewPager = view.findViewById(R.id.viewPager);
        tabLayout = view.findViewById(R.id.tabLayout);
        tabLayout.addTab(tabLayout.newTab().setText(sTitle[0]));
        tabLayout.addTab(tabLayout.newTab().setText(sTitle[1]));


        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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
        tabLayout.setupWithViewPager(viewPager);
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(UserActivityReservationFragment_tb1.newInstance());
        fragments.add(UserActivityReservationFragment_tb2.newInstance());

        UserActivityReservationFragment_Adapter adapter = new UserActivityReservationFragment_Adapter(getChildFragmentManager(),fragments, Arrays.asList(sTitle));
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
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
