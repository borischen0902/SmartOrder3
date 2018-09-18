package com.example.boris.smartorder3;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView;

import com.google.gson.JsonObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URL;

public class ImageTask extends AsyncTask<Object, Integer, Bitmap> {
    private final static String TAG = "ImageTask";
    private String url;
    private int id;
    private WeakReference<ImageView> imageViewWeakReference;

    //public ImageTask(String url, int id, int imageSize) {
    //    this(url, id, imageSize, null);
    //}

    public ImageTask(String url, int id, ImageView imageView) {
        this.url = url;
        this.id = id;
        //this.imageSize = imageSize;
        this.imageViewWeakReference = new WeakReference<>(imageView);
    }

    @Override
    protected Bitmap doInBackground(Object... params) {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("action", "getImage");
        jsonObject.addProperty("id", id);
        return getRemoteImage(url, jsonObject.toString());
    }

    @Override
    protected void onPostExecute(Bitmap bitmap) {
        ImageView imageView = imageViewWeakReference.get();
        if (isCancelled() || imageView == null) {
            return;
        }
        if (bitmap != null) {
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.info1);
        }
    }

    private Bitmap getRemoteImage(String url, String jsonOut) {
        HttpURLConnection con = null;
        Bitmap bitmap = null;
        try {
            con = (HttpURLConnection) new URL(url).openConnection();
            con.setDoInput(true);
            con.setDoOutput(true);
            con.setUseCaches(false);
            con.setRequestMethod("POST");
            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(con.getOutputStream()));
            bw.write(jsonOut);
            Log.d(TAG, "output: " + jsonOut);
            bw.close();

            int responseCode = con.getResponseCode();

            if (responseCode == 200) {
                bitmap = BitmapFactory.decodeStream(
                        new BufferedInputStream(con.getInputStream()));
            } else {
                Log.d(TAG, "response code: " + responseCode);
            }
        } catch (IOException e) {
            Log.e(TAG, e.toString());
        } finally {
            if (con != null) {
                con.disconnect();
            }
        }
        return bitmap;
    }
}