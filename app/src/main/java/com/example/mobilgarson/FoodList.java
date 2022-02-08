package com.example.mobilgarson;


import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.example.mobilgarson.Interface.ItemClickListener;
import com.example.mobilgarson.Model.Foods;
import com.example.mobilgarson.ViewHolder.FoodViewHolder;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

public class FoodList extends AppCompatActivity {

    private static final String TAG = "FoodList";

    RecyclerView recyclerView;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference foodList;

    String categoryId = "";

    //FirebaseRecyclerAdapter
    FirebaseRecyclerAdapter<Foods, FoodViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food_list);

        //Firebase
        database = FirebaseDatabase.getInstance();
        foodList = database.getReference("Foods");

        recyclerView = findViewById(R.id.recycler_food);
        //recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab2);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent cartIntent = new Intent(FoodList.this, Cart.class);
                startActivity(cartIntent);
            }
        });

        //Get intent here from Home_Activity to get CategoryId
        if (getIntent() != null){
            categoryId = getIntent().getStringExtra("CategoryId");
        }

        if(!categoryId.isEmpty() && categoryId != null){
            loadListFood(categoryId);
        }

    }

    //loadadListFood() method implementation
    private void loadListFood(String categoryId) {
        adapter = new FirebaseRecyclerAdapter<Foods, FoodViewHolder>(Foods.class,
                R.layout.food_item,
                FoodViewHolder.class,
                foodList.orderByChild("menuId").equalTo(categoryId) ////Like : Select * from Foods where MenuId =
        ){
            @Override
            protected void populateViewHolder(FoodViewHolder viewHolder, final Foods model, int position) {
                viewHolder.food_name.setText(model.getName());
                Picasso.get().load(model.getImage()).into(viewHolder.food_image);


                final Foods local = model;
                viewHolder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        //Start new Activity
                        Intent foodDetail = new Intent(FoodList.this, FoodDetails.class);
                        //Save food id to activity
                        foodDetail.putExtra("FoodId", adapter.getRef(position).getKey());
                        startActivity(foodDetail);
                    }
                });
            }
        };

        //Set Adapter
        recyclerView.setAdapter(adapter);
    }
}
