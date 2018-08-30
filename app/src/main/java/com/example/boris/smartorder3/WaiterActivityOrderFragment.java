package com.example.boris.smartorder3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class WaiterActivityOrderFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.waiter_order_fragment, container, false);
        RecyclerView rvOrder = (RecyclerView) view.findViewById(R.id.rvOrder);
        rvOrder.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvOrder.setAdapter(new orderAdapter(inflater, getOrder()));
        return view;
    }

    //假資料
    private List<COrder> getOrder() {
        List<COrder> order = new ArrayList<>();
        order.add(new COrder(5, 1, 2, 3, 4, 3, 2, 4));
        order.add(new COrder(2, 2, 3, 3, 4, 3, 2, 4));
        order.add(new COrder(3, 2, 2, 3, 4, 3, 2, 4));
        order.add(new COrder(4, 1, 4, 3, 4, 3, 2, 4));
        order.add(new COrder(1, 3, 4, 3, 4, 3, 2, 4));
        return order;
    }

    //把Data binding在View
    private class orderAdapter extends RecyclerView.Adapter<orderAdapter.MyViewHolder> {
        private LayoutInflater inflater;
        private List<COrder> order;

        public orderAdapter(LayoutInflater inflater, List<COrder> order) {
            this.inflater = inflater;
            this.order = order;
        }

        class MyViewHolder extends RecyclerView.ViewHolder {
            TextView tvTableID, tvItem1, tvItem2, tvItem3, tvItem4, tvItem5, tvItem6, tvItem7;

            public MyViewHolder(View order_item) {
                super(order_item);
                tvTableID = order_item.findViewById(R.id.tvTableID);
                tvItem1 = order_item.findViewById(R.id.tvItem1);
                tvItem2 = order_item.findViewById(R.id.tvItem2);
                tvItem3 = order_item.findViewById(R.id.tvItem3);
                tvItem4 = order_item.findViewById(R.id.tvItem4);
                tvItem5 = order_item.findViewById(R.id.tvItem5);
                tvItem6 = order_item.findViewById(R.id.tvItem6);
                tvItem7 = order_item.findViewById(R.id.tvItem7);
            }
        }

        @Override
        public int getItemCount() {
            return order.size();
        }

        @NonNull
        @Override
        public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
            View order_item = inflater.inflate(R.layout.waiter_order_fragment_item, viewGroup, false);
            return new MyViewHolder(order_item);
        }

        @Override
        public void onBindViewHolder(@NonNull final MyViewHolder myViewHolder, int i) {
            final COrder orderItem = order.get(i);
            myViewHolder.tvTableID.setText("桌號 : " + Integer.toString(orderItem.getTableID()));
            myViewHolder.tvItem1.setText(orderItem.getOrderItem1());
            myViewHolder.tvItem2.setText(orderItem.getOrderItem2());
            myViewHolder.tvItem3.setText(orderItem.getOrderItem3());
            myViewHolder.tvItem4.setText(orderItem.getOrderItem4());
            myViewHolder.tvItem5.setText(orderItem.getOrderItem5());
            myViewHolder.tvItem6.setText(orderItem.getOrderItem6());
            myViewHolder.tvItem7.setText(orderItem.getOrderItem7());
            myViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                }
            });
        }

    }
}
