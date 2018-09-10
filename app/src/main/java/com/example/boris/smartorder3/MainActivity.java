package com.example.boris.smartorder3;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;


public class MainActivity extends AppCompatActivity {
    private static final String TAG = "Login";
    EditText etAccount;
    EditText etPassword;
    private CCommonTask loginTask;
    private String account, password;
    private int permission;
    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_login);
        //isLogin(); 儲存設定檔功能, 開發階段關閉
        handleViews();
    }

    /* 是否登入過檢查 */
    private void isLogin() {
        boolean loginCheck;
        int activityIndex;
        SharedPreferences pref = getSharedPreferences(CCommon.LOGIN_INFO, MODE_PRIVATE);
        loginCheck = pref.getBoolean("login", false);
        if (loginCheck) {
            activityIndex = pref.getInt("permission", 0);
            goToActivity(activityIndex);
        }
    }

    /* 換頁 */
    private void goToActivity(int activityIndex) {
        switch (activityIndex) {
            case 1:
                intent = new Intent(this, UserActivity.class);
                break;
            case 2:
                intent = new Intent(this, WaiterActivity.class);
                break;
            case 3:
                intent = new Intent(this, AdminActivity.class);
                break;
            default:
                break;
        }
        startActivity(intent);
        finish();
    }

    /* 儲存使用者設定檔 */
    private void saveLoginInfo() {
        SharedPreferences pref = getSharedPreferences(CCommon.LOGIN_INFO, MODE_PRIVATE);
        pref.edit().putBoolean("login", true).putString("account", account).putString("password", password).putInt("permission", permission).apply();
        setResult(RESULT_OK);
    }


    private void handleViews() {
        etAccount = findViewById(R.id.etAccount);
        etPassword = findViewById(R.id.etPassword);
    }

    // 測試用按鍵
    public void goToAdmin(View view) {
        intent = new Intent(this, AdminActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToWaiter(View view) {
        intent = new Intent(this, WaiterActivity.class);
        startActivity(intent);
        finish();
    }

    public void goToUser(View view) {
        intent = new Intent(this, UserActivity.class);
        startActivity(intent);
        finish();
    }
    // -------------------------------------------


    public void btRegisterOnclick(View view) {
        intent = new Intent(this, MainActivityRegister.class);
        startActivity(intent);
        finish();
    }

    public void btLoginOnClick(View view) {
        Toast.makeText(this, "已成功登入", Toast.LENGTH_SHORT).show();
        account = etAccount.getText().toString();
        password = etPassword.getText().toString();
        if (CCommon.isNetworkConnected(this)) {
            String url = CCommon.URL + "/LoginServlet";
            JsonObject jsonObject = new JsonObject();
            jsonObject.addProperty("action", "checkAccount");
            jsonObject.addProperty("account", account);
            jsonObject.addProperty("password", password);
            String jsonOut = jsonObject.toString();
            loginTask = new CCommonTask(url, jsonOut);
            try {
                String jsonIn = loginTask.execute().get();
                jsonObject = new Gson().fromJson(jsonIn, JsonObject.class);
                permission = jsonObject.get("permission").getAsInt();

                switch (permission) {
                    case 0:
                        Toast.makeText(this, "請輸入正確帳號密碼或註冊", Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        saveLoginInfo();
                        goToActivity(1);
                        break;
                    case 2:
                        saveLoginInfo();
                        goToActivity(2);
                        break;
                    case 3:
                        saveLoginInfo();
                        goToActivity(3);
                        break;
                    default:
                        Toast.makeText(this, "請輸入正確帳號密碼或註冊", Toast.LENGTH_SHORT).show();
                        break;
                }
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        } else {
            Toast.makeText(this, "未連線", Toast.LENGTH_SHORT).show();
        }
    }

}
