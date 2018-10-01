package com.example.boris.smartorder3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.HashMap;
import java.util.LinkedList;


public class UserActivityReservationFragment_tb1 extends Fragment {

    //RecyclerView-1
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerViewAdapter recyclerViewAdapter;

    //假資料
    private LinkedList<HashMap<String,String>> data;

    private void doData(){
        data = new LinkedList<>();
        for (int i = 0; i < 100;i++){
            HashMap<String,String> row = new HashMap<>();
            int random = (int)(Math.random()*100);
            row.put("title" , "Title" + random);
            row.put("date" , "Date" + random);
            data.add(row);
        }
    }

    public static Fragment newInstance(){
        UserActivityReservationFragment_tb1 fragment = new UserActivityReservationFragment_tb1();
        return fragment;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.user_reservation_fragment_tb1,null);
        //RecyclerView-2  layout.user_reservation_fragment_tb1 使用RecyclerView並設定ID綁定
        //設定是否為固定大小後 需要設定LayoutManager
        recyclerView = view.findViewById(R.id.RecyclerView);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(layoutManager);


        //假資料
        doData();
        //產生Adapter 並於recyclerView 設定 Adapter
        recyclerViewAdapter = new RecyclerViewAdapter();
        recyclerView.setAdapter(recyclerViewAdapter);

        return view;
    }

    //RecyclerView-3 宣告內部類別 繼承 RecyclerView.Adapter 修正實作3個方法
    //private class RecyclerViewAdapter extends RecyclerView.Adapter{}
    private class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder>{

        //RecyclerView-４
        //宣告ViewHolder extends RecyclerView.ViewHolder 修正產生建構子 並於上方加入泛型<RecyclerViewAdapter.ViewHolder>
        //並修正實作方法名及傳入參數為 RecyclerViewAdapter.ViewHolder
        class ViewHolder extends RecyclerView.ViewHolder{

            public View itemView;

            //RecyclerView-6 宣告要控制的View 並於下方 ViewHolder 中進行連動
            public ImageView myImageView;
            public TextView myTextView1 , myTextView2 ;

            public Button button1, button2;

            public ViewHolder(View itemView) {
                super(itemView);
                this.itemView = itemView;

                myImageView = itemView.findViewById(R.id.ImageView);
                myTextView1 = itemView.findViewById(R.id.TextView1);
                myTextView2 = itemView.findViewById(R.id.TextView2);

                button1 = itemView.findViewById(R.id.Button1);
                button2 = itemView.findViewById(R.id.Button2);
            }
        }


        //RecyclerView-5  介紹要呈現的View 給ViewHolder
        @NonNull
        @Override
        public RecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemView = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.user_reservation_fragment_tb1_item,parent,false);
            ViewHolder viewHolder = new ViewHolder(itemView);
            return viewHolder;
        }
        //RecyclerView-7 綁定每筆資料要顯示的內容  並於getItemCount() 中設定有多少筆資料
        @Override
        public void onBindViewHolder(@NonNull RecyclerViewAdapter.ViewHolder holder, int position) {
            holder.myTextView1.setText(data.get(position).get("title"));
            holder.myTextView2.setText(data.get(position).get("date"));

            holder.button1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.get(2).put("title","修改");
                    data.get(2).put("date","2018-10-01");
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            });

            holder.button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    data.removeFirst();
                    recyclerViewAdapter.notifyDataSetChanged();
                }
            });
        }
        //資料筆數
        @Override
        public int getItemCount() {
            return data.size();
        }
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

