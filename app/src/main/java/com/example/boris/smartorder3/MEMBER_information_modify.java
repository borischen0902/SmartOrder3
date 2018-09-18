package com.example.boris.smartorder3;

import android.Manifest;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View.OnClickListener;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.widget.DatePicker;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MEMBER_information_modify extends AppCompatActivity  {

    private int mYear, mMonth, mDay;
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.member_show_modify_information);


        final Button dateButton = (Button)findViewById(R.id.dateButton);
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
                        String format =  setDateFormat(year, month, day);
                        dateButton.setText(format);
                    }

                }, mYear,mMonth, mDay).show();
            }
        });


        Button btok = findViewById(R.id.btOK);
        btok .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MEMBER_information_modify.this.finish();
            }
        });

        Button btclean = findViewById(R.id.btClean);
        btclean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                refresh();
            }
        });


    }



    private String setDateFormat(int year,int monthOfYear,int dayOfMonth){
        return String.valueOf(year) + "-"
                + String.valueOf(monthOfYear + 1) + "-"
                + String.valueOf(dayOfMonth);
    }



    public void refresh() {
        finish();
        Intent intent = new Intent(this,MEMBER_information_modify.class);
        startActivity(intent);
    }


}


