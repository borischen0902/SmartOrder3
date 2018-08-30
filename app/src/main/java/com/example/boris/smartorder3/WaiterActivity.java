package com.example.boris.smartorder3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;

public class WaiterActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.waiter_activity);
        BottomNavigationView navigation = findViewById(R.id.navigationBottom);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);
        initContent();
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.bnOrder:
                    changeFragment(new WaiterActivityOrderFragment());
                    //setTitle("出餐狀況");
                    return true;
                case R.id.bnStatus:
                    changeFragment(new WaiterActivityStatusFragment());
                    //setTitle("座位狀況");
                    return true;
                default:
                    initContent();
                    break;
            }
            return false;
        }

    };

    private void initContent() {
        changeFragment(new WaiterActivityOrderFragment());
        //setTitle("出餐狀況");
    }

    private void changeFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction =
                fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.content, fragment);
        fragmentTransaction.commit();
    }
}

