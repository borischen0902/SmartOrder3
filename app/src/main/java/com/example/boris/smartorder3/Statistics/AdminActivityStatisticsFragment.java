package com.example.boris.smartorder3.Statistics;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.boris.smartorder3.R;
import com.example.boris.smartorder3.Statistics.task.CommonTask;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;

public class AdminActivityStatisticsFragment extends Fragment {
    private final static String TAG = "StatisticsFragment";
    private TextView textView;
    private CommonTask newsGetAllTask;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.admin_activity_statistics_fragment, container, false);

        return view;
    }

    @Override           //OAC監聽兵團
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button stbutton = getActivity().findViewById(R.id.startButton);
        Button edbutton = getActivity().findViewById(R.id.endButton);
        Button clickLineChart = getActivity().findViewById(R.id.selectButton);
        stbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
        edbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog(v);
            }
        });
        clickLineChart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //初始化Intent物件
//                Intent intent = new Intent();
//                //從MainActivity 到Main2Activity
//                intent.setClass(this , LineChartActivity.class);
//                //開啟Activity
//                startActivity(intent);
                Intent intent = new Intent(getActivity(),LineChartActivity.class);
                startActivity(intent);
            }
        });

    }

    private void showAllNews() {
        if (Common.networkConnected(getActivity())) {
            String url = Common.URL + "/ChartServlet";
            List<Chart> chartList = null;
            try {
                JsonObject jsonObject = new JsonObject();
                jsonObject.addProperty("action", "getAll"); //add屬性 行動,值getAll
                String jsonOut = jsonObject.toString();                  //
                newsGetAllTask = new CommonTask(url, jsonOut);           //
                String jsonIn = newsGetAllTask.execute().get();
                Log.d(TAG, jsonIn);
                Gson gson = new Gson();
                Type listType = new TypeToken<List<Chart>>(){ }.getType();
                chartList = gson.fromJson(jsonIn, listType);
            } catch (Exception e) {
                Log.e(TAG, e.toString());
            }
            if (chartList == null || chartList.isEmpty()) {
                Common.showToast(getActivity(), R.string.msg_NoNewsFound);
            } else {
                textView.setText((CharSequence) chartList);
//                rvNews.setAdapter(new NewsRecyclerViewAdapter(getActivity(), chartList));
            }
        } else {
            Common.showToast(getActivity(), R.string.msg_NoNetwork);
        }
    }

    public void showDatePickerDialog(View v)        //時間挑選用
    {
        DialogFragment newFragment = new DatePickerFragment();
        Bundle bData = new Bundle();
        bData.putInt("view", v.getId());
        Button button = (Button) v;
        bData.putString("date", button.getText().toString());
        newFragment.setArguments(bData);
        newFragment.show(getFragmentManager(), "日期挑選器");
    }

}

