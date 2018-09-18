package com.example.boris.smartorder3;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

public class CCommonTask extends AsyncTask<String, Integer, String> {
    private final String TAG = "CCommonTask";
    private String url, outStr;

    public CCommonTask(String url, String outStr) {
        this.url = url;
        this.outStr = outStr;
    }

    @Override
    protected String doInBackground(String... params) {
        return getRemoteDate();
    }

    private String getRemoteDate() {
        HttpURLConnection con = null;
        StringBuilder inStr = new StringBuilder();
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setChunkedStreamingMode(0);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            con.setRequestProperty("charset", "UTF-8");
            BufferedWriter bw = new BufferedWriter((new OutputStreamWriter((con.getOutputStream()))));
            bw.write(outStr);
            Log.d(TAG, "output:" + outStr);
            bw.close();
            int responseCode = con.getResponseCode();
            if (responseCode == 200) {
                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream()));
                String line;
                while ((line = br.readLine()) != null) {
                    inStr.append(line);
                }
            } else {
                Log.d(TAG, "response code:" + responseCode);
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        Log.d(TAG, "input:" + inStr);
        return inStr.toString();

    }
}
