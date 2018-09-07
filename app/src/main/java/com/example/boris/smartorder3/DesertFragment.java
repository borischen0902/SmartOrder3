package com.example.boris.smartorder3;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;



public class DesertFragment extends Fragment {


    public DesertFragment() {
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_desert, container, false);
    }

    public static Fragment newInstance() {
        DesertFragment fragment = new DesertFragment();
        return fragment;
    }
}
