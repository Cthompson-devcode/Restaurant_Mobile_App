package com.example.donnasdiner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.example.donnasdiner.Adapter.BreakfastAdapter;
import com.example.donnasdiner.Database.restaurantRepository;
import com.example.donnasdiner.Entities.BreakfastEntity;
import com.example.donnasdiner.ViewModel.breakfastViewModel;
import com.example.donnasdiner.ViewModel.searchViewModel;

import java.util.List;

public class searchActivity extends AppCompatActivity {


    private String selectedFilter = "all";
    private String currentSearchText = "";
    private SearchView searchView;
    private BreakfastAdapter adapter;
    private BreakfastAdapter mAdapter;
    public static final int ADD_BREAKFAST_REQUEST = 1;
    public static final int EDIT_BREAKFAST_REQUEST = 2;
    public static String EXTRA_BREAKFAST_ID =
            "com.example.donnasdiner.EXTRA_BREAKFAST_ID";
    private RecyclerView recyclerView;
    com.example.donnasdiner.Database.restaurantRepository restaurantRepository;
    private com.example.donnasdiner.ViewModel.searchViewModel searchViewModel;
    Context context;
    public static int EXTRA_CHANGE_NOTED =
            1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        setTitle("Search Menu");


        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        BreakfastAdapter adapter = new BreakfastAdapter(); //(breakfastSearch);
        recyclerView.setAdapter(adapter);


        searchViewModel = ViewModelProviders.of(this).get(searchViewModel.class);
        searchViewModel.getAllBreakfast().observe(this, new Observer<List<BreakfastEntity>>() {
            @Override
            public void onChanged(@Nullable List<BreakfastEntity> breakfast) {

                adapter.setBreakfast(breakfast);


            }
        });


        adapter.setonItemClickListener(new BreakfastAdapter.onItemClickListener() {
            @Override

            public void onItemClick(BreakfastEntity breakfastEntity) {
                //sending data that was clicked
                Intent intent = new Intent(searchActivity.this, userViewBreakfastItemActivity.class);
                intent.putExtra(userViewBreakfastItemActivity.EXTRA_BREAKFAST_ID, String.valueOf(breakfastEntity.getBreakfastID()));
                intent.putExtra(userViewBreakfastItemActivity.EXTRA_BREAKFAST_TITLE, breakfastEntity.getBreakfastTitle());
                intent.putExtra(userViewBreakfastItemActivity.EXTRA_BREAKFAST_DESCRIPTION, breakfastEntity.getBreakfastDetails());
                intent.putExtra(userViewBreakfastItemActivity.EXTRA_BREAKFAST_PRICE, breakfastEntity.getBreakfastPrice());
                startActivityForResult(intent, EDIT_BREAKFAST_REQUEST);

            }
        });


    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.search_view, menu);

        // getMenuInflater().inflate(R.menu.search_view, menu);
        MenuItem menuItem = menu.findItem(R.id.action_search);

        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {



                if (query != null) {
                    searchViewModel.setFilter(query);
                }
                return true;
            }


            @Override
            public boolean onQueryTextChange(String newText) {
                if (newText != null) {


                   searchViewModel.setFilter(newText);









                    Intent intent = new Intent();
                    intent.putExtra(String.valueOf(EXTRA_CHANGE_NOTED), 2);

                    System.out.println("THIS IS MY NEWTEXT==========" + newText);

                }

                return true;

            }


        });
        return true;
    }
}