package com.example.boris.smartorder3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

import com.example.boris.smartorder3.Statistics.AdminActivityStatisticsFragment;

public class AdminActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_activity);

        BottomNavigationView navigation = findViewById(R.id.navigationBottom);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        initContent();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            Fragment fragment;
            switch (item.getItemId()) {
                case R.id.bnForm:
                    fragment = new AdminActivityStatisticsFragment();
                    changeFragment(fragment);
                    setTitle("統計報表");
                    return true;
                case R.id.bnInformation:
                    fragment = new AdminActivityCouponFragment();
                    changeFragment(fragment);
                    setTitle("活動設定");
                    return true;
                case R.id.bnMessage:
                    fragment = new AdminActivityReMessageFragment();
                    changeFragment(fragment);
                    setTitle("留言管理");
                    return true;
                default:
                    initContent();
                    break;
            }
            return false;
        }

    };

    private void initContent() {
        Fragment fragment = new AdminActivityStatisticsFragment();
        changeFragment(fragment);
        setTitle("統計頁面");
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }



}


