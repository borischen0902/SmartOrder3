package com.example.boris.smartorder3;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

public class MainActivityRegister extends AppCompatActivity {
    RadioGroup rgSex;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_register);
        handleViews();
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {

            }
        });
    }

    private void handleViews() {
        rgSex = findViewById(R.id.rgSex);

    }


    public void btRegisterConfirmOnclick(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        finish();
    }
}
