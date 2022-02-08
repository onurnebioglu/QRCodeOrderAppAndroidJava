package com.example.mobilgarson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.example.mobilgarson.Common.Common;
import com.example.mobilgarson.Model.Request;
import com.example.mobilgarson.ViewHolder.OrderViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class OrderStatus extends AppCompatActivity {

    public RecyclerView recyclerView;
    public RecyclerView.LayoutManager layoutManager;


    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference requests;

    FirebaseRecyclerAdapter<Request, OrderViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_status);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        recyclerView = findViewById(R.id.listOrder);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        //Telefon numarasına göre sipariş bilgileri getirme
        loadOrders(Common.currentUser.getPhone());
    }

    //Sipariş bilgilerni getirme metodu
    private void loadOrders(String phone) {
        adapter = new FirebaseRecyclerAdapter<Request, OrderViewHolder>(
                Request.class,
                R.layout.order_layout,
                OrderViewHolder.class,

                requests.orderByChild("phone").equalTo(phone)


        ) {
            @Override
            protected void populateViewHolder(OrderViewHolder viewHolder, Request model, int position) {

                viewHolder.txtOrderStatus.setText(convertCodeToStatus(model.getStatus()));
                viewHolder.txtOrderAddress.setText(model.getTable());
                viewHolder.txtOrderPhone.setText(model.getPhone());
                viewHolder.txtOrderTotal.setText(model.getTotal());
                viewHolder.txtOrderStatus.setBackgroundResource(convertCodeToColor(model.getStatus()));
            }
        };
        recyclerView.setAdapter(adapter);
    }

    //Sipariş durumununa göre string bilgisi alma
    private String convertCodeToStatus(String status) {
        if (status.equals("0")){




            return "Alındı";
        }
        if (status.equals("1"))
        {
            return "Hazırlanıyor";

        }
        else {


            return "Servis Edildi";
        }

    }
    //Sipariş durumuna göre bilginin rengini değiştirme
    private int convertCodeToColor(String status) {
        OrderViewHolder viewHolder;
        if (status.equals("0")){




            return R.drawable.button_background_2;
        }
        if (status.equals("1"))
        {
            return R.drawable.button_background_4;

        }
        else {
            return R.drawable.button_background_3;
        }

    }
}
