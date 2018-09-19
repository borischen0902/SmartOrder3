package com.example.boris.smartorder3;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.FileProvider;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.lang.*;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;
import static com.example.boris.smartorder3.UserActivityReservationFragment_tb2.TAG;

public class MEMBER_informationFragment extends Fragment {


    private ImageView imageView;
    private Button btTakePictureLarge, btPickPicture;
    private TextView tvphone,tvpassword,tvname,tvbir,tvsex;
    private File file;
    private static final int REQUEST_TAKE_PICTURE_LARGE = 1;
    private static final int REQUEST_PICK_PICTURE = 2;
    CCommonTask registerTask;
    CAccount personalFile;
    
    public static Fragment newInstance(){
        MEMBER_informationFragment fragment = new MEMBER_informationFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_show_information,null);
        SharedPreferences pref = getActivity().getSharedPreferences(CCommon.LOGIN_INFO, MODE_PRIVATE);//取得設定檔資料
        readservlet(pref.getString("account",""));
        handleview(view);
        Botton(view);
        return view;
    }

    private void readservlet(String pref) {
        if (CCommon.isNetworkConnected(getActivity())) {
            String url = CCommon.URL + "/SmartOrderServlet";
            JsonObject jsonObject = new JsonObject();
            Gson gson = new Gson();
            jsonObject.addProperty("action", "readAccount"); //Servlet switch
            jsonObject.addProperty("account", gson.toJson(pref));
            String jsonOut = jsonObject.toString();
            registerTask = new CCommonTask(url, jsonOut);

            try {
                //傳到Servlet 並且等待結果
                String jsonIn = registerTask.execute().get();
                jsonObject = new Gson().fromJson(jsonIn, JsonObject.class);

                //回傳到Android資料
                String read = jsonObject.get("readaccount").getAsString();
                personalFile = gson.fromJson(read, CAccount.class);

            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
        } else {
            Toast.makeText(getActivity(), "未連線", Toast.LENGTH_SHORT).show();
        }


    }

    public boolean isIntentAvailable(Context context, Intent intent) {
        PackageManager packageManager = context.getPackageManager();
        List<ResolveInfo> list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY);
        return list.size() > 0;
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        if (resultCode == RESULT_OK) {
            int newSize = 512;
            switch (requestCode) {
                case REQUEST_TAKE_PICTURE_LARGE:
                    Bitmap srcPicture = BitmapFactory.decodeFile(file.getPath());
                    Bitmap downsizedPicture = InformationCommon.downSize(srcPicture, newSize);
                    imageView.setImageBitmap(downsizedPicture);
                    break;
                case REQUEST_PICK_PICTURE:
                    Uri uri = intent.getData();
                    if (uri != null) {
                        String[] columns = {MediaStore.Images.Media.DATA};
                        Cursor cursor = getActivity().getContentResolver().query(uri, columns,
                                null, null, null);
                        if (cursor != null && cursor.moveToFirst()) {
                            String imagePath = cursor.getString(0);
                            cursor.close();
                            Bitmap srcImage = BitmapFactory.decodeFile(imagePath);
                            Bitmap downsizedImage = InformationCommon.downSize(srcImage, newSize);
                            imageView.setImageBitmap(downsizedImage);
                        }
                    }
                    break;
            }
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        String[] permissions = {Manifest.permission.WRITE_EXTERNAL_STORAGE};
        InformationCommon.askPermissions(getActivity(), permissions, InformationCommon.REQ_EXTERNAL_STORAGE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        switch (requestCode) {
            case InformationCommon.REQ_EXTERNAL_STORAGE:
                if (grantResults.length > 0 &&
                        grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    btTakePictureLarge.setEnabled(true);
                    btPickPicture.setEnabled(true);
                } else {
                    btTakePictureLarge.setEnabled(false);
                    btPickPicture.setEnabled(false);
                }
                break;
        }
    }

    private void handleview(View view) {
        /*顯示資料*/
        tvphone =view.findViewById(R.id.Showinformation);
        tvpassword =view.findViewById(R.id.Showpassword);
        tvname =view.findViewById(R.id.Showname);
        tvbir =view.findViewById(R.id.ShowDate);
        tvsex =view.findViewById(R.id.Showsex);

        tvphone.setText(personalFile.getPhone());
        tvpassword.setText(personalFile.getPassword());
        tvname.setText(personalFile.getName());
        tvbir.setText(personalFile.getBirth());

        int sex;
        sex = personalFile.getSex();

        if(sex==0){
            tvsex.setText("男性");
        }else{
            tvsex.setText("女性");
        }



    }

    private void Botton(View view) {
        Button btmodify = view.findViewById(R.id.btmodify);
        btmodify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), MEMBER_information_modify.class);
                startActivity(intent);
            }
        });
        //相機
        imageView = view.findViewById(R.id.photo);
        btTakePictureLarge =view.findViewById(R.id.btTakePictureLarge);
        btPickPicture = view.findViewById(R.id.btPickPicture);
        btTakePictureLarge.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
                file = new File(file, "picture.jpg");
                Uri contentUri = FileProvider.getUriForFile(getActivity(), getActivity().getPackageName() + ".provider", file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, contentUri);
                if (isIntentAvailable(getActivity(), intent)) {
                    getActivity().startActivityForResult(intent, REQUEST_TAKE_PICTURE_LARGE);
                } else {
                    Toast.makeText(getActivity(), R.string.textNoCameraAppsFound, Toast.LENGTH_SHORT).show();
                }
            }
        });

        btPickPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getActivity().startActivityForResult(intent, REQUEST_PICK_PICTURE);
            }
        });
    }


    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
