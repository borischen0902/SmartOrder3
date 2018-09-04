package com.example.boris.smartorder3;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class WaiterActivityStatusFragment extends Fragment {
    private List<CStatus> status = new ArrayList<>();
    private StatusAdapter statusAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.waiter_status_fragment, container, false);
        RecyclerView rvStatus = (RecyclerView) view.findViewById(R.id.rvStatus);
        rvStatus.setLayoutManager(new LinearLayoutManager(getActivity()));
        status = getStatus();
        statusAdapter = new StatusAdapter(inflater, status);
        rvStatus.setAdapter(statusAdapter);
        ItemTouchHelper.Callback callback = new SwipeCardCallBack(status, statusAdapter);
        ItemTouchHelper helper = new ItemTouchHelper(callback);
        helper.attachToRecyclerView(rvStatus);
        return view;
    }

    private List<CStatus> getStatus() {
        status.add(new CStatus(2, "11:02", "12:00", 0));
        status.add(new CStatus(1, "11:05", "12:02", 1));
        status.add(new CStatus(3, "11:06", "12:03", 0));
        status.add(new CStatus(4, "11:08", "12:04", 0));
        //shortByTime(status);
        return status;
    }

    /*
    private class dateSorting implements Comparator<CStatus> {
private

        @Override
        public int compare(CStatus o1, CStatus o2) {
            return 0;
        }
    }
*/

    private class StatusAdapter extends RecyclerView.Adapter<StatusAdapter.MyViewHolder> {
        private LayoutInflater inflater;
        private List<CStatus> status;

        public StatusAdapter(LayoutInflater inflater, List<CStatus> status) {
            this.inflater = inflater;
            this.status = status;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvTableID;

            public MyViewHolder(View itemView) {
                super(itemView);
                tvTableID = itemView.findViewById(R.id.tvTableID);
            }
        }

        @Override
        public int getItemCount() {
            for (int i = 0; i < status.size(); i++) {
                if (status.get(i).getCurrentStatus() == 1) {
                    status.remove(i);
                }
            }
            return status.size();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View status_item = inflater.inflate(R.layout.waiter_status_fragment_item, parent, false);
            return new MyViewHolder(status_item);
        }

        @Override
        public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
            CStatus statusItem = status.get(position);
            holder.tvTableID.setText(Integer.toString(statusItem.getTableID()));

        }

    }

    private class SwipeCardCallBack extends ItemTouchHelper.SimpleCallback {
        /**
         * Creates a Callback for the given drag and swipe allowance. These values serve as
         * defaults
         * and if you want to customize behavior per ViewHolder, you can override
         * {@link #getSwipeDirs(RecyclerView, ViewHolder)}
         * and / or {@link #getDragDirs(RecyclerView, ViewHolder)}.
         *
         * @param dragDirs  Binary OR of direction flags in which the Views can be dragged. Must be
         * composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
         * #END},
         * {@link #UP} and {@link #DOWN}.
         * @param swipeDirs Binary OR of direction flags in which the Views can be swiped. Must be
         * composed of {@link #LEFT}, {@link #RIGHT}, {@link #START}, {@link
         * #END},
         * {@link #UP} and {@link #DOWN}.
         */
        private List<CStatus> status;
        private StatusAdapter statusAdapter;

        public SwipeCardCallBack(List<CStatus> status, StatusAdapter statusAdapter) {
            super(0,
                    ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
            this.status = status;
            this.statusAdapter = statusAdapter;
        }

        public SwipeCardCallBack(int dragDirs, int swipeDirs) {
            super(dragDirs, swipeDirs);
        }

        @Override
        public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
            CStatus statusItem = status.get(viewHolder.getLayoutPosition());
            statusItem.setCurrentStatus(1);
            statusAdapter.notifyDataSetChanged();
        }
    }
}
