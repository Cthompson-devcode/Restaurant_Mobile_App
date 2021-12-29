package com.example.donnasdiner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.donnasdiner.Adapter.DinnerAdapter;
import com.example.donnasdiner.Adapter.LunchAdapter;
import com.example.donnasdiner.Entities.DinnerEntity;
import com.example.donnasdiner.Entities.LunchEntity;
import com.example.donnasdiner.ViewModel.DinnerViewModel;
import com.example.donnasdiner.ViewModel.LunchViewModel;

import java.util.List;

public class userDinnerActivity extends AppCompatActivity {

    DinnerViewModel dinnerViewModel; public static final int EDIT_BREAKFAST_REQUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dinner);

            setTitle("Dinner Menu");

            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);


            DinnerAdapter adapter = new DinnerAdapter(); //(breakfastSearch);
            recyclerView.setAdapter(adapter);


            dinnerViewModel = ViewModelProviders.of(this).get(DinnerViewModel.class);
            dinnerViewModel.getAllDinner().observe(this, new Observer<List<DinnerEntity>>() {
                @Override
                public void onChanged(@Nullable List<DinnerEntity> dinner) {

                    adapter.setDinner(dinner);


                }
            });


            adapter.setonItemClickListener(new DinnerAdapter.onItemClickListener() {
                @Override

                public void onItemClick(DinnerEntity dinnerEntity) {
                    //sending data that was clicked
                    Intent intent = new Intent(userDinnerActivity.this, userViewBreakfastItemActivity.class);
                    intent.putExtra(userViewBreakfastItemActivity.EXTRA_BREAKFAST_ID, String.valueOf(dinnerEntity.getdinnerID()));
                    intent.putExtra(userViewBreakfastItemActivity.EXTRA_BREAKFAST_TITLE, dinnerEntity.getdinnerTitle());
                    intent.putExtra(userViewBreakfastItemActivity.EXTRA_BREAKFAST_DESCRIPTION, dinnerEntity.getdinnerDetails());
                    intent.putExtra(userViewBreakfastItemActivity.EXTRA_BREAKFAST_PRICE, dinnerEntity.getdinnerPrice());
                    startActivityForResult(intent, EDIT_BREAKFAST_REQUEST);

                }
            });
        }


    }
