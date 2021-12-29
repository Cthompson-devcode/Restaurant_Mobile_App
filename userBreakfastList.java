package com.example.donnasdiner;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.core.view.MenuItemCompat;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;

import android.view.View;
import android.widget.SearchView.OnQueryTextListener;

import android.content.Context;
import android.content.Intent;
import android.net.sip.SipSession;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Toast;
import androidx.appcompat.widget.SearchView;

//import android.support.v7.app.AppCompatActivity;

import com.example.donnasdiner.Adapter.BreakfastAdapter;
import com.example.donnasdiner.Database.restaurantRepository;
import com.example.donnasdiner.Entities.BreakfastEntity;
import com.example.donnasdiner.ViewModel.breakfastViewModel;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;

public class userBreakfastList extends AppCompatActivity {
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
    restaurantRepository restaurantRepository;
    private breakfastViewModel breakfastViewModel;
    Context context;
    public static int EXTRA_CHANGE_NOTED =
            1;


    List<BreakfastAdapter> list = new ArrayList<>();

    private List<BreakfastEntity> breakfastSearch;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_breakfast_list);

        setTitle("Breakfast Menu");

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);


        BreakfastAdapter adapter = new BreakfastAdapter(); //(breakfastSearch);
        recyclerView.setAdapter(adapter);


        breakfastViewModel = ViewModelProviders.of(this).get(breakfastViewModel.class);
        breakfastViewModel.getAllBreakfast().observe(this, new Observer<List<BreakfastEntity>>() {
            @Override
            public void onChanged(@Nullable List<BreakfastEntity> breakfast) {

                adapter.setBreakfast(breakfast);


            }
        });


        adapter.setonItemClickListener(new BreakfastAdapter.onItemClickListener() {
            @Override

            public void onItemClick(BreakfastEntity breakfastEntity) {
                //sending data that was clicked
                Intent intent = new Intent(userBreakfastList.this, userViewBreakfastItemActivity.class);
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

        searchView.setOnSearchClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(userBreakfastList.this, searchActivity.class);
                startActivity(intent);
            }
        });
        return true;
    }
}









