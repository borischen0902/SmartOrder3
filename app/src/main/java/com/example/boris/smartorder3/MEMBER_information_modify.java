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
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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

import static com.example.boris.smartorder3.UserActivityReservationFragment_tb2.TAG;

public class MEMBER_information_modify extends AppCompatActivity  {
    Button dateButton;
    EditText Password,Name,rePassword;
    TextView tvphone;
    RadioGroup sex;
    MemberAccount account;
    int sexcheck,permission;
    CCommonTask registerTask;
    CAccount personalFile;
    String phone,password,name,bir,repassword;
    private int mYear, mMonth, mDay;
    Gson gson;
    boolean isBirthClick = false;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_show_modify_information);
        //isLogin(); //儲存設定檔功能, 開發階段關閉
        SharedPreferences pref = this.getSharedPreferences(CCommon.LOGIN_INFO, MODE_PRIVATE);//取得設定檔資料
        readservlet(pref.getString("account",""));
        DateButton();
        BTOK();
        BTclean();
        handview();

    }

    private void handview() {

        tvphone = findViewById(R.id.modify_Account);
        Password = findViewById(R.id.modify_PassWord);
        rePassword = findViewById(R.id.modify_rePassWord);
        Name = findViewById(R.id.modify_Name);
        sex = findViewById(R.id.Sex);
        if(personalFile!=null) {

            tvphone.setText(personalFile.getPhone());
            Password.setText(personalFile.getPassword());
            Name.setText(personalFile.getName());


        }

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

                isBirthClick = true;
            }
        });

    }

    private void readservlet(String pref) {

        if (CCommon.isNetworkConnected(this)) {
            String url = CCommon.URL + "/SmartOrderServlet";
            JsonObject jsonObject = new JsonObject();

            jsonObject.addProperty("action", "readAccount"); //Servlet switch
            jsonObject.addProperty("account", pref);
            String jsonOut = jsonObject.toString();
            registerTask = new CCommonTask(url, jsonOut);

            try {
                //傳到Servlet 並且等待結果
                String jsonIn = registerTask.execute().get();
                jsonObject = new Gson().fromJson(jsonIn, JsonObject.class);

                //回傳到Android資料
                String read = jsonObject.get("readaccount").getAsString();
                Gson gson = new Gson();
                personalFile = gson.fromJson(read, CAccount.class);

            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        } else {
            Toast.makeText(this, "未連線", Toast.LENGTH_SHORT).show();
        }


    }

    //按下ok回傳值
    private void BTOK() {
        Button btok = findViewById(R.id.btOK);
        btok .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setinformation();
            }
        });
    }

    private void setinformation() {

        password = Password.getText().toString();
        repassword=rePassword.getText().toString();
        name = Name.getText().toString();

        if(password.equals(repassword)){
           account = new MemberAccount(password,name,bir,sexcheck);
        }else{
            Toast.makeText(this, "請密碼錯誤", Toast.LENGTH_SHORT).show();
        }

        if (checkInt( password,repassword, name)) {
            if (CCommon.isNetworkConnected(this)) {
                String url = CCommon.URL + "/SmartOrderServlet";
                JsonObject jsonObject = new JsonObject();
                gson = new Gson();
                jsonObject.addProperty("action", "changeregister");
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
            if (permission == 1) {
                saveLoginInfo();
                Intent intent = new Intent(this,UserActivity.class);
                startActivity(intent);
                finish();
            }else {
                Toast.makeText(this, "此帳號已註冊", Toast.LENGTH_SHORT).show();
            }

        }

    }

    private boolean checkInt(String password,String repassword, String name) {


        if( (password.equals("") | repassword.equals(""))){
            if(!(password.equals(repassword))){
                Toast.makeText(this, "請密碼錯誤", Toast.LENGTH_SHORT).show();
            }
            Toast.makeText(this, "請輸入密碼", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (name.equals("")) {
            Toast.makeText(this, "請輸入姓名", Toast.LENGTH_SHORT).show();
            return false;
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


