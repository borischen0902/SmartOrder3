package com.example.boris.smartorder3;


import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;
import static android.content.Context.MODE_APPEND;
import static android.content.Context.MODE_PRIVATE;


public class DesertFragment extends Fragment {
    private final static String TAG = "DesertFragment";
    boolean[] check = new boolean[4];
    private RecyclerView rvDesert;
    private CCommonTask desertGetAllTask;
    private DesertImageTask DesertImageTask;
    private FragmentActivity activity;


    public DesertFragment() {

    }

    public static Fragment newInstance() {
        DesertFragment fragment;
        fragment = new DesertFragment();
        return fragment;
    }




    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_desert, container, false);
        rvDesert = view.findViewById(R.id.rvDesert);
        rvDesert.setLayoutManager(new LinearLayoutManager(getActivity()));
        Button btnDesertCheck = view.findViewById(R.id.btnDesertCheck);
        btnDesertCheck.setOnClickListener(confirmDesertCheck);
        return view;
    }

    private Button.OnClickListener confirmDesertCheck = new Button.OnClickListener() {
        @Override
        public void onClick(View v) {
            new AlertDialog.Builder(getActivity())
                    .setTitle("加入至付款？")
                    .setMessage("餐點選購完畢，請至付款，完成點餐")
                    .setPositiveButton("確定", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                    Log.d(TAG,"activity"+getActivity());
                                    SharedPreferences pref = getActivity().getSharedPreferences(CCommon.DESERT_INFO, MODE_PRIVATE);

                                    if (check[0]) {
                                        pref.edit().putString("水信玄餅", "水信玄餅").apply();
                                        getActivity().setResult(RESULT_OK);

                                    } else {

                                        pref.edit().putString("水信玄餅", "").apply();
                                        getActivity().setResult(RESULT_OK);

                                    }


                                    if (check[1]) {

                                        pref.edit().putString("糯米丸子", "糯米丸子").apply();
                                        getActivity().setResult(RESULT_OK);

                                    } else {

                                        pref.edit().putString("糯米丸子", "").apply();
                                        getActivity().setResult(RESULT_OK);


                                    }
                                    if (check[2]) {

                                        pref.edit().putString("抹茶蛋糕", "抹茶蛋糕").apply();
                                        getActivity().setResult(RESULT_OK);

                                    } else {

                                        pref.edit().putString("抹茶蛋糕", "").apply();
                                        getActivity().setResult(RESULT_OK);


                                    }
                                    if (check[3]) {

                                        pref.edit().putString("抹茶冰淇淋", "抹茶冰淇淋").apply();
                                        getActivity().setResult(RESULT_OK);

                                    } else {

                                        pref.edit().putString("抹茶冰淇淋", "").apply();
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
    };

    @Override
    public void onStart() {
        super.onStart();
        showAllDesert();
    }

    private void showAllDesert() {
        if (CCommon.isNetworkConnected(getActivity())) {
            String url = CCommon.URL + "/SmartOrderServlet";
            List<Desert> desertList = null;

            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "desertGetAll");
                String jsonOut = jsonObject.toString();
                desertGetAllTask = new CCommonTask(url, jsonOut);
                String jsonIn = desertGetAllTask.execute().get();
                Log.d(TAG, jsonIn);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Desert>>(){ }.getType();
                desertList = gson.fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (desertList == null || desertList.isEmpty())  {

            } else {
                rvDesert.setAdapter(new DesertFragment.DesertRecyclerViewAdapter(getActivity(), desertList));
            }
        } else {
        }
    }

    private class DesertRecyclerViewAdapter extends RecyclerView.Adapter<DesertFragment.DesertRecyclerViewAdapter.MyViewHolder> {
        private Context context;
        private List<Desert> desertList;


        public DesertRecyclerViewAdapter(Context context, List<Desert> desertList) {
            this.context = context;
            this.desertList = desertList;

        }

        @Override
        public int getItemCount() {

            return desertList.size();
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public void onBindViewHolder(@NonNull final DesertFragment.DesertRecyclerViewAdapter.MyViewHolder myViewHolder, final int i) {
            final Desert desertItem = desertList.get(i);

            String url = CCommon.URL + "/SmartOrderServlet";
            final int id = desertItem.getId();
            DesertImageTask = new DesertImageTask(url, id, myViewHolder.imageView);
            DesertImageTask.execute();
            myViewHolder.txtName.setText(String.valueOf(desertItem.getName()));
            myViewHolder.txtPrice.setText(String.valueOf(desertItem.getPrice()));
            SharedPreferences pref = getActivity().getSharedPreferences(CCommon.DRINK_INFO, MODE_PRIVATE);
            switch (i){
                case 0:
                    if ((pref.getString("水信玄餅","").equals("水信玄餅"))){
                        myViewHolder.btnButton.setChecked(true);
                    }else  myViewHolder.btnButton.setChecked(false);

                    break;

                case 1:
                    if ((pref.getString("糯米丸子","").equals("糯米丸子"))){
                        myViewHolder.btnButton.setChecked(true);
                    }else  myViewHolder.btnButton.setChecked(false);

                    break;

                case 2:
                    if ((pref.getString("抹茶蛋糕","").equals("抹茶蛋糕"))){
                        myViewHolder.btnButton.setChecked(true);
                    }else  myViewHolder.btnButton.setChecked(false);

                    break;

                case 3:
                    if ((pref.getString("抹茶冰淇淋","").equals("抹茶冰淇淋"))){
                        myViewHolder.btnButton.setChecked(true);
                    }else  myViewHolder.btnButton.setChecked(false);

                    break;


            }

            myViewHolder.btnButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView,boolean isChecked) {

                    switch (id) {

                        case 6:

                            check[0] = myViewHolder.btnButton.isChecked();


                            break;


                        case 7:

                            check[1] = myViewHolder.btnButton.isChecked();

                            break;


                        case 8:

                            check[2] = myViewHolder.btnButton.isChecked();


                            break;


                        case 9:

                            check[3] = myViewHolder.btnButton.isChecked();

                            break;

                    }



                }
            });


        }

        @NonNull
        @Override
        public DesertFragment.DesertRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View itemView = LayoutInflater.from(context).inflate(R.layout.desert_item_view, viewGroup, false);
            return new DesertFragment.DesertRecyclerViewAdapter.MyViewHolder(itemView);
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            ImageView imageView;
            TextView txtName, txtPrice;
            CheckBox btnButton;

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

