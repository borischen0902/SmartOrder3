package com.example.boris.smartorder3;

import android.support.annotation.NonNull;
import android.support.v4.app.FragmentActivity;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

class NormalRecyclerViewAdapter extends RecyclerView.Adapter<MEMBER_informationFragment.ItemAdapter.MyViewHolder> {
    public NormalRecyclerViewAdapter(FragmentActivity activity, Object p1) {
    }

    @NonNull
    @Override
    public MEMBER_informationFragment.ItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull MEMBER_informationFragment.ItemAdapter.MyViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
