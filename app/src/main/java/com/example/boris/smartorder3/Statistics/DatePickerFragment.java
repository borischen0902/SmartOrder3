package com.example.boris.smartorder3.Statistics;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.widget.Button;
import android.widget.DatePicker;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DatePickerFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener
{
    int vid;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState)
    {
        super.onCreateDialog(savedInstanceState);

        //欲轉換的日期字串
        Bundle bData = getArguments();
        // 記錄下傳進來的是哪個 button 的 id
        vid = bData.getInt("view");
        String str = bData.getString("date");
        final Calendar c = Calendar.getInstance();
        Date date;
        if(!str.equals(""))
        {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            //進行轉換
            ParsePosition pos = new ParsePosition(0);
            date = sdf.parse(str, pos);
            c.setTime(date);
        }
        int year = c.get(Calendar.YEAR);
        int month = c.get(Calendar.MONTH);
        int day = c.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog datePickerDialog = new DatePickerDialog(getActivity(),this, year, month,day);
        datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());
        // Create a new instance of DatePickerDialog and return it
        return datePickerDialog;

        // 建立 DatePickerDialog instance 並回傳
//        return new DatePickerDialog(getActivity(), this, year, month, day);
    }

    public void onDateSet(DatePicker view, int year, int month, int day)
    {
        String m = String.valueOf((month)+1);
        String d = String.valueOf(day);
        if (month <= 9 ) {
            m = "0"+ m;
        }if (day <= 9 ) {
        d = "0" + d;
        }
        Button button = getActivity().findViewById(vid);
        // 注意 月的起始值是 0，所以要加 1
        button.setText(""+year+"-"+m+"-"+d);
    }
}
