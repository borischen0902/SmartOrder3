package com.example.boris.smartorder3;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
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
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MEMBER_informationFragment extends Fragment {
    private RecyclerView recyclerView;

    public static Fragment newInstance(){
        MEMBER_informationFragment fragment = new MEMBER_informationFragment();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.member_informationfragment,null);
        handleview(view);
        return view;
    }

    private void handleview(View view) {
        recyclerView=view.findViewById(R.id.reinformation);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        recyclerView.setAdapter(new ItemAdapter(getRecyclerView_Item(),getContext()));

    }

    private class ItemAdapter extends RecyclerView.Adapter<MEMBER_informationFragment.ItemAdapter.MyViewHolder> {
        List<RecyclerView_Item> items;
        Context context;

        public ItemAdapter(List<RecyclerView_Item> items, Context context) {
            this.items = items;
            this.context = context;
        }

        @Override
        public int getItemCount() {
            return items.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView tvName;

            public MyViewHolder(View item_view) {
                super(item_view);

            }
        }

        public MEMBER_informationFragment.ItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View item_view = layoutInflater.inflate(R.layout.member_show_information, parent, false);
            return new MEMBER_informationFragment.ItemAdapter.MyViewHolder(item_view);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {

        }


    }

    private List<RecyclerView_Item> getRecyclerView_Item() {
        List<RecyclerView_Item> Items = new ArrayList<>();

        Items.add(new RecyclerView_Item(1, 2222));

        return Items;
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
