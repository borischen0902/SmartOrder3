package com.example.boris.smartorder3;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.text.format.DateUtils;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.time.Year;
import java.util.Calendar;

public class MainActivityRegister extends AppCompatActivity {
    private static final String TAG = "Register";
    EditText etAccountRegister, etPasswordRegister, etPasswordConfirm, etNameRegister;
    RadioGroup rgSex;
    TextView tvBirthdayRegister;
    String phone, password, passwordConfirm, name, birthday;
    int sex, permission;
    CAccount account;
    CCommonTask registerTask;
    Gson gson;
    boolean isBirthClick = false;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_register);
        handleViews();
        rgSex.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.rbMale:
                        sex = 0;
                        break;
                    case R.id.rbFemale:
                        sex = 1;
                        break;
                    default:
                        sex = 0;
                        break;
                }
            }
        });
        tvBirthdayRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar calendar = Calendar.getInstance();
                DatePickerDialog dialog = new DatePickerDialog(MainActivityRegister.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int day) {
                                birthday = setDateFormat(year, month, day);
                                tvBirthdayRegister.setText("您的生日為 : " + birthday);
                            }
                        },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DAY_OF_MONTH));
                isBirthClick = true;
                dialog.show();
            }
        });

    }

    private String setDateFormat(int year, int month, int day) {
        return Integer.toString(year) + "-" + Integer.toString(month) + "-" + Integer.toString(day);
    }

    private void handleViews() {
        etAccountRegister = findViewById(R.id.etAccountRegister);
        etPasswordRegister = findViewById(R.id.etPasswordRegister);
        etPasswordConfirm = findViewById(R.id.etPasswordConfirm);
        etNameRegister = findViewById(R.id.etNameRegister);
        rgSex = findViewById(R.id.rgSex);
        tvBirthdayRegister = findViewById(R.id.tvBirthdayRegister);
    }


    public void btRegisterConfirmOnclick(View view) {
        phone = etAccountRegister.getText().toString();
        password = etPasswordRegister.getText().toString();
        passwordConfirm = etPasswordConfirm.getText().toString();
        name = etNameRegister.getText().toString();
        account = new CAccount(phone, password, name, birthday, sex);

        if (checkInput(phone, password, passwordConfirm, name)) {
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
                    Log.e(TAG, e.toString());
                }
            } else {
                Toast.makeText(this, "未連線", Toast.LENGTH_SHORT).show();
            }
            if (permission == 1) {
                saveLoginInfo();
                Intent intent = new Intent(this, UserActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(this, "此帳號已註冊", Toast.LENGTH_SHORT).show();
            }
        }
    }

    /* 輸入檢查 */
    private boolean checkInput(String phone, String password, String passwordConfirm, String name) {
        if ((!phone.matches("[0-9]{10}"))) {
            Toast.makeText(this, "請確認帳號是否正確", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (!(password.equals(passwordConfirm)) || password.equals("") | passwordConfirm.equals("")) {
            Toast.makeText(this, "請確認密碼是否正確", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (name.equals("")) {
            Toast.makeText(this, "請輸入姓名", Toast.LENGTH_SHORT).show();
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
}
