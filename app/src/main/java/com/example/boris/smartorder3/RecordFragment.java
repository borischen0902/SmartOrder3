package com.example.boris.smartorder3;


import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class RecordFragment extends Fragment {



    public RecordFragment() {

    }

    public static Fragment newInstance() {
        RecordFragment fragment = new RecordFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_menu_record, container, false);

        return view;
    }

    interface SendMessage {
        void sendData(String message);
    }


}
