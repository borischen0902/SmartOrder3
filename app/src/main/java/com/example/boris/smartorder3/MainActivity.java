package com.example.boris.smartorder3;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    EditText etAccount;
    EditText etPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);
        handleViews();
    }


    private void handleViews() {
        etAccount = findViewById(R.id.etAccount);
        etPassword = findViewById(R.id.etPassword);
    }


    public void goToAdmin(View view) {
        Intent intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToWaiter(View view) {
        Intent intent = new Intent(this, WaiterActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToUser(View view) {
        Intent intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        finish();
    }

    public void btRegisterOnclick(View view) {
        Intent intent = new Intent(this, MainActivityRegister.class);
        startActivity(intent);
        finish();
    }

    public void btLoginOnClick(View view) {
        Toast.makeText(this, "已點擊登入", Toast.LENGTH_SHORT).show();
    }

}
