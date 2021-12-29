package com.example.donnasdiner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import com.example.donnasdiner.Adapter.BreakfastAdapter;
import com.example.donnasdiner.Adapter.LunchAdapter;
import com.example.donnasdiner.Entities.BreakfastEntity;
import com.example.donnasdiner.Entities.LunchEntity;
import com.example.donnasdiner.ViewModel.LunchViewModel;
import com.example.donnasdiner.ViewModel.breakfastViewModel;

import java.util.List;

public class userLunchActivity extends AppCompatActivity {
    public static final int EDIT_BREAKFAST_REQUEST = 2;
    LunchViewModel lunchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_lunch);

        setTitle("Lunch Menu");


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        LunchAdapter adapter = new LunchAdapter(); //(breakfastSearch);
        recyclerView.setAdapter(adapter);


        lunchViewModel = ViewModelProviders.of(this).get(LunchViewModel.class);
        lunchViewModel.getAllLunch().observe(this, new Observer<List<LunchEntity>>() {
            @Override
            public void onChanged(@Nullable List<LunchEntity> lunch) {

                adapter.setLunch(lunch);


            }
        });


        adapter.setonItemClickListener(new LunchAdapter.onItemClickListener() {
            @Override

            public void onItemClick(LunchEntity lunchEntity) {
                //sending data that was clicked
                Intent intent = new Intent(userLunchActivity.this, userViewBreakfastItemActivity.class);
                intent.putExtra(userViewBreakfastItemActivity.EXTRA_BREAKFAST_ID, String.valueOf(lunchEntity.getlunchID()));
                intent.putExtra(userViewBreakfastItemActivity.EXTRA_BREAKFAST_TITLE, lunchEntity.getlunchTitle());
                intent.putExtra(userViewBreakfastItemActivity.EXTRA_BREAKFAST_DESCRIPTION, lunchEntity.getlunchDetails());
                intent.putExtra(userViewBreakfastItemActivity.EXTRA_BREAKFAST_PRICE, lunchEntity.getlunchPrice());
                startActivityForResult(intent, EDIT_BREAKFAST_REQUEST);

            }
        });
    }


}
