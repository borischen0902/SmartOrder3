package com.example.boris.smartorder3;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class DesertFragment extends Fragment {
    private RecyclerView recyclerView;

    public DesertFragment() {

    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_desert, container, false);
        recyclerView = view.findViewById(R.id.rvDesert);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        List<CDesert> desertList = getDesertList();
        recyclerView.setAdapter(new DesertFragment.ItemAdapter(getActivity(), desertList));
        return view;


    }

    public static Fragment newInstance() {
        DesertFragment fragment = new DesertFragment();
        return fragment;
    }



    private List<CDesert> getDesertList () {

        List<CDesert> desertList = new ArrayList<>();

        desertList.add(new CDesert(R.drawable.raindrop, "水信玄餅", 70, true));
        desertList.add(new CDesert(R.drawable.dango, "糯米丸子", 60, true));
        desertList.add(new CDesert(R.drawable.cake, "抹茶蛋糕", 60, true));
        desertList.add(new CDesert(R.drawable.ice, "抹茶冰淇淋", 50, true));
        return desertList;
    }


    private class ItemAdapter extends RecyclerView.Adapter<DesertFragment.ItemAdapter.MyViewHolder> {
        private Context context;
        private List<CDesert> desertList;




        public ItemAdapter(Context context, List<CDesert> desertList) {
            this.context = context;
            this.desertList = desertList;

        }

        @NonNull
        @Override
        public DesertFragment.ItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.desert_item_view, viewGroup, false);
            return new DesertFragment.ItemAdapter.MyViewHolder(itemView);




        }

        @Override
        public void onBindViewHolder(@NonNull DesertFragment.ItemAdapter.MyViewHolder myViewHolder, int i) {


            final CDesert desertItem = desertList.get(i);
            myViewHolder.imageView.setImageResource(desertItem.getImage());
            myViewHolder.txtName.setText(String.valueOf(desertItem.getName()));
            myViewHolder.txtPrice.setText(String.valueOf(desertItem.getPrice()));
            myViewHolder.btnButton.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View view) {



                }
            });

        }

        @Override
        public int getItemCount() {

            return desertList.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView txtName, txtPrice;
            Button btnButton;

            MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.ivDesertPhoto);
                txtName = itemView.findViewById(R.id.txtDesertName);
                txtPrice = itemView.findViewById(R.id.txtDesertPrice);
                btnButton = itemView.findViewById(R.id.btnDesertButton);
            }

        }

    }



    }

