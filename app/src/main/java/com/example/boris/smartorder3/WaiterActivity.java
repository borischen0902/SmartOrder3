package com.example.boris.smartorder3;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.MenuItem;

public class WaiterActivity extends AppCompatActivity {
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        new AlertDialog.Builder(this)
                /* 設定標題 */
                .setTitle("即將離開app並登出")
                /* 設定圖示 */
                //.setIcon(R.drawable.alert)
                /* 設定訊息文字 */
                .setMessage("是否確定離開")
                /* 設定positive與negative按鈕上面的文字與點擊事件監聽器 */
                .setPositiveButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                })
                .setNegativeButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                }).show();
        return super.onKeyDown(keyCode, event);
    }

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

