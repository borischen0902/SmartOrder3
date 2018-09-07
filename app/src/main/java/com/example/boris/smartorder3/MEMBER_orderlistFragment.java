package com.example.boris.smartorder3;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class MEMBER_orderlistFragment extends Fragment {

    private RecyclerView orderrecyclerView;

    public static Fragment newInstance(){
        MEMBER_orderlistFragment orderfragment = new MEMBER_orderlistFragment();
        return orderfragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater ordrtinflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = ordrtinflater.inflate(R.layout.member_orderlistfragment,null);
        Review(view);
        return view;
    }

    private void Review(View view) {
        orderrecyclerView=view.findViewById(R.id.reorderlist);
        orderrecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
        orderrecyclerView.setAdapter(new MEMBER_orderlistFragment.orderItemAdapter(getRecyclerView_Item(),getContext()));

    }

    private class orderItemAdapter extends RecyclerView.Adapter<MEMBER_orderlistFragment.orderItemAdapter.MyViewHolder> {
        List<RecyclerView_Item> items;
        Context context;

        public orderItemAdapter(List<RecyclerView_Item> items, Context context) {
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

        public MEMBER_orderlistFragment.orderItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(context);
            View item_view = layoutInflater.inflate(R.layout.member_show_orderlist, parent, false);
            return new MEMBER_orderlistFragment.orderItemAdapter.MyViewHolder(item_view);
        }
        public void onBindViewHolder(@NonNull MEMBER_orderlistFragment.orderItemAdapter.MyViewHolder holder, int position) {

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
