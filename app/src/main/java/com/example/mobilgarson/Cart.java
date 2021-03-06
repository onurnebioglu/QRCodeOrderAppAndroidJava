package com.example.mobilgarson;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;

import android.widget.TextView;
import android.widget.Toast;

import com.example.mobilgarson.Common.Common;
import com.example.mobilgarson.Database.Database;
import com.example.mobilgarson.Model.Order;
import com.example.mobilgarson.Model.Request;
import com.example.mobilgarson.ViewHolder.CartAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;


public class Cart extends AppCompatActivity {

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    //Firebase Database
    FirebaseDatabase database;
    DatabaseReference requests;

    TextView textTotalPrice;
    Button btnPlace;
    Button btnClean;

    List<Order> cart = new ArrayList<>();

    CartAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);

        //Firebase
        database = FirebaseDatabase.getInstance();
        requests = database.getReference("Requests");

        //init
        recyclerView = findViewById(R.id.listCart);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        textTotalPrice = findViewById(R.id.total_price);
        btnPlace = findViewById(R.id.btn_place_order);
        btnClean=findViewById(R.id.btn_clean_order);


        btnPlace.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String empty= new Database(getBaseContext()).isEmpty();
               if (empty=="false")
               {
                   Toast.makeText(Cart.this,"Sepetiniz bo??",Toast.LENGTH_SHORT).show();

               }else
                   {showAlertDialog();}
            }
        });

        loadListFood();

        btnClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String empty= new Database(getBaseContext()).isEmpty();
                if (empty=="false")
                {
                    Toast.makeText(Cart.this,"Sepetiniz zaten bo??",Toast.LENGTH_SHORT).show();

                }else
                {showAlertDialog2();}


            }
        });
    }

    // Listeyi veritaban??na aktarma Metodu
    private void showAlertDialog() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("Son bir ad??m");
        alertDialog.setMessage("Sipari??ini onaylamak istedi??inize emin misiniz?");



        alertDialog.setIcon(R.drawable.ic_shopping_cart);

        alertDialog.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String table=("Masa Numaras??: "+Common.currentUser.getTable());


                //Yeni istek olu??turma
                Request request = new Request(
                        Common.currentUser.getPhone(),
                        Common.currentUser.getName(),
                        table,
                        textTotalPrice.getText().toString(),
                        cart
                );

                //Firebas verileri girme
                //System.CurrentMills anahtar i??in kullan??yoruz
                requests.push().setValue(request);

                //Sepeti temizleme
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Te??ekk??rler, sipari??iniz al??nd??", Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        alertDialog.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();

    }

   //Sepete t??m veilerin ??ekilmesi
    private void loadListFood() {
        cart = new Database(this).getCarts();
        adapter = new CartAdapter(cart, this);
        recyclerView.setAdapter(adapter);

        //??cret hesaplama
        int total = 0;
        for (Order order : cart){
            total += (Integer.parseInt(order.getPrice()))*(Integer.parseInt(order.getQuantity()));
        }
        NumberFormat fmt = NumberFormat.getInstance();
        textTotalPrice.setText("???"+fmt.format(total));
    }

    private void showAlertDialog2() {
        final AlertDialog.Builder alertDialog = new AlertDialog.Builder(Cart.this);
        alertDialog.setTitle("Uyar??");
        alertDialog.setMessage("Sepeti temizlemek istedi??inize emin misiniz?");



        alertDialog.setIcon(R.drawable.ic_shopping_cart);

        alertDialog.setPositiveButton("EVET", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {


                //Sepeti temizle
                new Database(getBaseContext()).cleanCart();
                Toast.makeText(Cart.this, "Sepetiniz temizlendi", Toast.LENGTH_SHORT).show();
                Intent cartIntent = new Intent(Cart.this, Cart.class);
                startActivity(cartIntent);
                finish();
            }
        });

        alertDialog.setNegativeButton("HAYIR", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });

        alertDialog.show();

    }


}
