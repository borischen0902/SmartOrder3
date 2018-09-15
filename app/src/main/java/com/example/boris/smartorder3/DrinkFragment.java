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

import java.util.ArrayList;
import java.util.List;

public class DrinkFragment extends Fragment {
    private RecyclerView recyclerView;

    public DrinkFragment() {

    }


    @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_drink, container, false);
        recyclerView = view.findViewById(R.id.rvDrink);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        List<CDrink> drinkList = getDrinkList();
        recyclerView.setAdapter(new ItemAdapter(getActivity(), drinkList));
        return view;


        }

        private List<CDrink> getDrinkList () {

            List<CDrink> drinkList = new ArrayList<>();

            drinkList.add(new CDrink(R.drawable.matcha, "抹茶", 70, true));
            drinkList.add(new CDrink(R.drawable.matcha_latte, "抹茶拿鐵", 60, true));
            drinkList.add(new CDrink(R.drawable.matcha_smoothie, "抹茶奶昔", 60, true));
            drinkList.add(new CDrink(R.drawable.beer, "啤酒", 50, true));
            return drinkList;
        }

    public static Fragment newInstance() {
        DrinkFragment fragment = new DrinkFragment();
        return fragment;
    }


    private class ItemAdapter extends RecyclerView.Adapter<DrinkFragment.ItemAdapter.MyViewHolder> {
            private Context context;
            private List<CDrink> drinkList;


            public ItemAdapter(Context context, List<CDrink> drinkList) {
                this.context = context;
                this.drinkList = drinkList;

            }

            @NonNull
            @Override
            public DrinkFragment.ItemAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(context).inflate(R.layout.drink_item_view, viewGroup, false);
                return new DrinkFragment.ItemAdapter.MyViewHolder(itemView);
            }

            @Override
            public void onBindViewHolder(@NonNull DrinkFragment.ItemAdapter.MyViewHolder myViewHolder, int i) {

                final CDrink drinkItem = drinkList.get(i);
                myViewHolder.imageView.setImageResource(drinkItem.getImage());
                myViewHolder.txtName.setText(String.valueOf(drinkItem.getName()));
                myViewHolder.txtPrice.setText(String.valueOf(drinkItem.getPrice()));
                myViewHolder.btnButton.setOnClickListener(new Button.OnClickListener() {

                    @Override
                    public void onClick(View view) {




                    }
                });
            }

            @Override
            public int getItemCount() {

                return drinkList.size();
            }

            class MyViewHolder extends RecyclerView.ViewHolder {
                ImageView imageView;
                TextView txtName, txtPrice;
                Button btnButton;

                MyViewHolder(View itemView) {
                    super(itemView);
                    imageView = itemView.findViewById(R.id.ivDrinkPhoto);
                    txtName = itemView.findViewById(R.id.txtDrinkName);
                    txtPrice = itemView.findViewById(R.id.txtDrinkPrice);
                    btnButton = itemView.findViewById(R.id.btnDrinkButton);
                }

            }

        }

    }
