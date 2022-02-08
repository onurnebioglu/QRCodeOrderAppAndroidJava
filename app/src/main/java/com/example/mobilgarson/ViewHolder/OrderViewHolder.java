package com.example.mobilgarson.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.example.mobilgarson.R;

public class OrderViewHolder extends RecyclerView.ViewHolder{

    public TextView  txtOrderStatus, txtOrderPhone, txtOrderAddress, txtOrderTotal;




    public OrderViewHolder(View itemView) {
        super(itemView);


        txtOrderStatus = itemView.findViewById(R.id.order_status);
        txtOrderPhone = itemView.findViewById(R.id.order_phone);
        txtOrderAddress = itemView.findViewById(R.id.order_address);
        txtOrderTotal = itemView.findViewById(R.id.order_total);

    }


}
