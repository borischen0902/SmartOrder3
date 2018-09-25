package com.example.boris.smartorder3;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.util.LongSparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_PRIVATE;

public class DrinkFragment extends Fragment {
    private final static String TAG = "DrinkFragment";
    boolean[] check = new boolean[4];
    private RecyclerView rvDrink;
    private CCommonTask drinkGetAllTask;
    private DrinkImageTask DrinkImageTask;


    public DrinkFragment() {

    }

    public static Fragment newInstance() {
        DrinkFragment fragment;
        fragment = new DrinkFragment();
        return fragment;
    }

    @Override
        public View onCreateView (@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_drink, container, false);
        rvDrink = view.findViewById(R.id.rvDrink);
        rvDrink.setLayoutManager(new LinearLayoutManager(getActivity()));
        Button btnDrinkCheck = view.findViewById(R.id.btnDrinkCheck);
        btnDrinkCheck.setOnClickListener(new Button.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(getActivity())
                        .setTitle("加入至付款？")
                        .setMessage("餐點選購完畢，請至付款，完成點餐")
                        .setPositiveButton("確定", new DialogInterface.OnClickListener() {

                            @Override
                                    public void onClick(DialogInterface dialog, int which) {

                                        Log.d(TAG,"activity"+getActivity());
                                        SharedPreferences pref = getActivity().getSharedPreferences(CCommon.DRINK_INFO, MODE_PRIVATE);

                                        if (check[0]) {
                                            pref.edit().putString("抹茶", "抹茶").apply();
                                            getActivity().setResult(RESULT_OK);

                                        }else {

                                            pref.edit().putString("抹茶", "").apply();
                                            getActivity().setResult(RESULT_OK);

                                        }


                                        if (check[1]) {

                                            pref.edit().putString("抹茶拿鐵", "抹茶拿鐵").apply();
                                            getActivity().setResult(RESULT_OK);

                                        } else {

                                            pref.edit().putString("抹茶拿鐵", "").apply();
                                            getActivity().setResult(RESULT_OK);


                                        } if (check[2]) {

                                            pref.edit().putString("抹茶奶昔", "抹茶奶昔").apply();
                                            getActivity().setResult(RESULT_OK);

                                        } else {

                                            pref.edit().putString("抹茶奶昔", "").apply();
                                            getActivity().setResult(RESULT_OK);


                                        }if (check[3]) {

                                            pref.edit().putString("啤酒", "啤酒").apply();
                                            getActivity().setResult(RESULT_OK);

                                        }else {

                                            pref.edit().putString("啤酒", "").apply();
                                            getActivity().setResult(RESULT_OK);

                                        }




                                    }





                        })

                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {


                            }
                        })
                        .show();

            }
        });
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

    private class DrinkRecyclerViewAdapter extends RecyclerView.Adapter<DrinkFragment.DrinkRecyclerViewAdapter.MyViewHolder> {
            private Context context;
            private List<Drink> drinkList;


            public DrinkRecyclerViewAdapter(Context context, List<Drink> drinkList) {
                this.context = context;
                this.drinkList = drinkList;

            }

        @Override
        public int getItemCount() {

            return drinkList.size();
        }


        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, final int i) {
                final Drink drinkItem = drinkList.get(i);

            String url = CCommon.URL + "/SmartOrderServlet";
            final int id = drinkItem.getId();
            DrinkImageTask = new DrinkImageTask(url, id, myViewHolder.imageView);
            DrinkImageTask.execute();
            myViewHolder.txtName.setText(String.valueOf(drinkItem.getName()));
            myViewHolder.txtPrice.setText(String.valueOf(drinkItem.getPrice()));
            SharedPreferences pref = getActivity().getSharedPreferences(CCommon.DRINK_INFO, MODE_PRIVATE);
            switch (i){
                case 0:
                    if ((pref.getString("抹茶","").equals("抹茶"))){
                        myViewHolder.btnButton.setChecked(true);

                    }else  myViewHolder.btnButton.setChecked(false);

                    break;


                case 1:
                    if ((pref.getString("抹茶拿鐵","").equals("抹茶拿鐵"))){
                        myViewHolder.btnButton.setChecked(true);

                    }else  myViewHolder.btnButton.setChecked(false);

                    break;

                case 2:
                    if ((pref.getString("抹茶奶昔","").equals("抹茶奶昔"))){
                        myViewHolder.btnButton.setChecked(true);

                    }else  myViewHolder.btnButton.setChecked(false);

                    break;

                case 3:
                    if ((pref.getString("啤酒","").equals("啤酒"))){
                        myViewHolder.btnButton.setChecked(true);

                    }else  myViewHolder.btnButton.setChecked(false);

                    break;


            }

            myViewHolder.btnButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                    switch (id) {

                        case 2:

                            check[0] = myViewHolder.btnButton.isChecked();


                            break;


                        case 3:

                            check[1] = myViewHolder.btnButton.isChecked();

                            break;


                        case 4:

                            check[2] = myViewHolder.btnButton.isChecked();


                            break;


                        case 5:

                            check[3] = myViewHolder.btnButton.isChecked();

                            break;

                    }



                }
            });


        }

            @NonNull
            @Override
            public DrinkFragment.DrinkRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                View itemView = LayoutInflater.from(context).inflate(R.layout.drink_item_view, viewGroup, false);
                return new DrinkFragment.DrinkRecyclerViewAdapter.MyViewHolder(itemView);
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

        }

    }
