package com.example.boris.smartorder3;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class DrinkFragment extends Fragment {
    private final static String TAG = "DrinkFragment";
    private RecyclerView rvDrink;
    private CCommonTask drinkGetAllTask;
    private DrinkImageTask DrinkImageTask;


    public DrinkFragment() {

    }


    @Override
        public View onCreateView (LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_drink, container, false);
        rvDrink = view.findViewById(R.id.rvDrink);
        rvDrink.setLayoutManager(new StaggeredGridLayoutManager(1, StaggeredGridLayoutManager.VERTICAL));
        showAllDrinks();
        return view;

        }


    @Override
    public void onStart() {
        super.onStart();
        showAllDrinks();
    }

    private void showAllDrinks() {
        if (CCommon.isNetworkConnected(getActivity())) {
            String url = CCommon.URL + "/SmartOrderServlet";
            List<Drink> drinkList = null;

            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "drinkGetAll");
                String jsonOut = jsonObject.toString();
                drinkGetAllTask = new CCommonTask(url, jsonOut);
                String jsonIn = drinkGetAllTask.execute().get();
                Log.d(TAG, jsonIn);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Drink>>(){ }.getType();
                drinkList = gson.fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (drinkList == null || drinkList.isEmpty())  {

            } else {
                rvDrink.setAdapter(new DrinkRecyclerViewAdapter(getActivity(), drinkList));
            }
        } else {
        }
    }


    public static Fragment newInstance() {
        DrinkFragment fragment = new DrinkFragment();
        return fragment;
    }


    private class DrinkRecyclerViewAdapter extends RecyclerView.Adapter<DrinkFragment.DrinkRecyclerViewAdapter.MyViewHolder> {
            private Context context;
            private List<Drink> drinkList;


            public DrinkRecyclerViewAdapter(Context context, List<Drink> drinkList) {
                this.context = context;
                this.drinkList = drinkList;

            }


        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView txtName, txtPrice;
            CheckBox btnButton;

            MyViewHolder(View itemView) {
                super(itemView);
                imageView = itemView.findViewById(R.id.ivDrinkPhoto);
                txtName = itemView.findViewById(R.id.txtDrinkName);
                txtPrice = itemView.findViewById(R.id.txtDrinkPrice);
                btnButton = itemView.findViewById(R.id.btnDrinkButton);
            }

        }


        @Override
        public int getItemCount() {

            return drinkList.size();
        }


        @Override
        public void onBindViewHolder(@NonNull DrinkFragment.DrinkRecyclerViewAdapter.MyViewHolder myViewHolder, int i) {
            final Drink drinkItem = drinkList.get(i);


            String url = CCommon.URL + "/SmartOrderServlet";
            int id = drinkItem.getId();
            DrinkImageTask = new DrinkImageTask(url, id, myViewHolder.imageView);
            DrinkImageTask.execute();

            myViewHolder.txtName.setText(String.valueOf(drinkItem.getName()));
            myViewHolder.txtPrice.setText(String.valueOf(drinkItem.getPrice()));
            myViewHolder.btnButton.setOnClickListener(new Button.OnClickListener() {

                @Override
                public void onClick(View view) {


                }
            });
        }

            @NonNull
            @Override
            public DrinkFragment.DrinkRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(context).inflate(R.layout.drink_item_view, viewGroup, false);
                return new DrinkFragment.DrinkRecyclerViewAdapter.MyViewHolder(itemView);
            }

        }

    }
