package com.example.boris.smartorder3;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MEMBER_information_modify extends AppCompatActivity  {
    Button dateButton;
    EditText Account,Password,Name,rePassword;
    RadioGroup sex;
    MemberAccount account;
    int sexcheck;
    int permission;
    CCommonTask registerTask;
    String phone,password,name,bir,repassword;
    private int mYear, mMonth, mDay;
    Gson gson;
    boolean isBirthClick = false;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_show_modify_information);

        DateButton();
        BTOK();
        BTclean();
        handview();

    }

    private void handview() {
        Account = findViewById(R.id.modify_Account);
        Password = findViewById(R.id.modify_PassWord);
        rePassword = findViewById(R.id.modify_rePassWord);
        Name = findViewById(R.id.modify_Name);
        sex = findViewById(R.id.Sex);


        sex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.Male:
                        sexcheck = 0;
                        break;
                    case R.id.Female:
                        sexcheck = 1;
                        break;
                    default:
                        sexcheck = 0;
                        break;
                }
            }
        });
    }

    //設定生日日期
    private void DateButton() {
        dateButton = (Button)findViewById(R.id.dateButton);
        dateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                final Calendar c = Calendar.getInstance();
                mYear = c.get(Calendar.YEAR);
                mMonth = c.get(Calendar.MONTH);
                mDay = c.get(Calendar.DAY_OF_MONTH);
                new DatePickerDialog(MEMBER_information_modify.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int day) {
                        bir =  String.valueOf(year) + "-"
                                + String.valueOf(month + 1) + "-"
                                + String.valueOf(day);
                        dateButton.setText(bir);
                    }

                }, mYear,mMonth, mDay).show();
            }
        });

    }

    //按下ok回傳值
    private void BTOK() {
        Button btok = findViewById(R.id.btOK);
        btok .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setinformation();
                MEMBER_information_modify.this.finish();
            }
        });
    }

    private void setinformation() {
        phone = Account.getText().toString();
        password = Password.getText().toString();
        repassword=rePassword.getText().toString();
        name = Name.getText().toString();
        account = new MemberAccount(phone,password,repassword,name,bir,sexcheck);

        if (checkInt(phone, password,repassword, name)) {
            if (CCommon.isNetworkConnected(this)) {
                String url = CCommon.URL + "/SmartOrderServlet";
                JsonObject jsonObject = new JsonObject();
                gson = new Gson();
                jsonObject.addProperty("action", "register");
                jsonObject.addProperty("account", gson.toJson(account));
                String jsonOut = jsonObject.toString();
                registerTask = new CCommonTask(url, jsonOut);
                try {
                    String jsonIn = registerTask.execute().get();
                    jsonObject = new Gson().fromJson(jsonIn, JsonObject.class);
                    permission = jsonObject.get("permission").getAsInt();
                } catch (Exception e) {
                }
            } else {
                Toast.makeText(this, "未連線", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private boolean checkInt(String phone,String password,String repassword, String name) {

        if(!phone.matches("[0-9]{10}")){
            Toast.makeText(this, "請輸入手機電話", Toast.LENGTH_SHORT).show();
            return false;
        }
        if(  password.equals("") | repassword.equals("")){
            if(!(password.equals(repassword))){
                Toast.makeText(this, "請密碼錯誤", Toast.LENGTH_SHORT).show();
            }else{
                Toast.makeText(this, "請輸入密碼", Toast.LENGTH_SHORT).show();
            }
            return false;
        }
        if (name.equals("")) {
            Toast.makeText(this, "請輸入姓名", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (permission == 1) {
            saveLoginInfo();
        }
        if (!isBirthClick) {
            Toast.makeText(this, "請選擇生日", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    /* 儲存使用者設定檔 */
    private void saveLoginInfo() {
        SharedPreferences pref = getSharedPreferences(CCommon.LOGIN_INFO, MODE_PRIVATE);
        pref.edit().putBoolean("login", true).putString("account", account.getPhone()).putString("password", account.getPassword()).putInt("permission", account.getPermission()).apply();
        setResult(RESULT_OK);
    }


    //按下clean清除
    private void BTclean() {
        Button btclean = findViewById(R.id.btClean);
        btclean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });

    }

    //重新整理
    public void refresh() {
        finish();
        Intent intent = new Intent(this,MEMBER_information_modify.class);
        startActivity(intent);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (registerTask != null) {
            registerTask.cancel(true);
            registerTask = null;
        }
    }

}


