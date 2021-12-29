package com.example.donnasdiner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.donnasdiner.Adapter.LunchAdapter;
import com.example.donnasdiner.Adapter.LunchAdapter;
import com.example.donnasdiner.Entities.BreakfastEntity;
import com.example.donnasdiner.Entities.LunchEntity;
import com.example.donnasdiner.ViewModel.LunchViewModel;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class lunchListActivity extends AppCompatActivity {

    public static final int ADD_LUNCH_REQUEST = 1;
    public static final int EDIT_LUNCH_REQUEST = 2;
    public static  String EXTRA_LUNCH_ID =
            "com.example.donnasdiner.EXTRA_LUNCH_ID";

    private com.example.donnasdiner.Database.restaurantRepository restaurantRepository;
    private LunchViewModel lunchViewModel1;

    private LunchViewModel lunchViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lunch_list);


        setTitle("Lunch Menu");
        FloatingActionButton buttonAddLunch = findViewById(R.id.button_add_breakfast);
        buttonAddLunch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (lunchListActivity.this, addEditLunch.class);
                startActivityForResult(intent, ADD_LUNCH_REQUEST);

                intent.putExtra(addEditLunch.EXTRA_LUNCH_ID, String.valueOf(-1));
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        LunchAdapter adapter = new LunchAdapter();
        recyclerView.setAdapter(adapter);


        lunchViewModel1 = ViewModelProviders.of(this).get(LunchViewModel.class);
        lunchViewModel1.getAllLunch().observe(this, new Observer<List<LunchEntity>>() {
            @Override
            public void onChanged(@Nullable List<LunchEntity> lunch) {

                adapter.setLunch(lunch);
            }
        });

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0,
                ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {

                LunchEntity lunchEntity = adapter.getLunchAt(viewHolder.getAdapterPosition());
                LunchViewModel lunchViewModel = new LunchViewModel(getApplication());




                lunchViewModel.delete(adapter.getLunchAt(viewHolder.getAdapterPosition()));

                Toast.makeText(lunchListActivity.this, "Lunch Item Deleted! ", Toast.LENGTH_SHORT).show();
            }


        }).attachToRecyclerView(recyclerView);

        adapter.setonItemClickListener(new LunchAdapter.onItemClickListener() {
            @Override

            public void onItemClick(LunchEntity lunchEntity) {
                //sending data that was clicked


            }
        });

        adapter.setonItemClickListener(new LunchAdapter.onItemClickListener() {
            @Override

            public void onItemClick(LunchEntity lunchEntity) {
                //sending data that was clicked
                Intent intent = new Intent(lunchListActivity.this, addEditLunch.class);
                intent.putExtra(addEditLunch.EXTRA_LUNCH_ID, String.valueOf(lunchEntity.getLunchID()));
                intent.putExtra(addEditLunch.EXTRA_LUNCH_TITLE, lunchEntity.getlunchTitle());
                intent.putExtra(addEditLunch.EXTRA_LUNCH_DESCRIPTION, lunchEntity.getlunchDetails());
                intent.putExtra(addEditLunch.EXTRA_LUNCH_PRICE, lunchEntity.getlunchPrice());
                startActivityForResult(intent, EDIT_LUNCH_REQUEST);

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("THIS IS MY REQUEST CODE+++++++++++==" + requestCode);

        if (requestCode == ADD_LUNCH_REQUEST && resultCode == RESULT_OK)
        {
            String title = data.getStringExtra(addEditLunch.EXTRA_LUNCH_TITLE);
            String description = data.getStringExtra(addEditLunch.EXTRA_LUNCH_DESCRIPTION);
            String price = data.getStringExtra(addEditLunch.EXTRA_LUNCH_PRICE);
            int lunchID = data.getIntExtra(addEditLunch.EXTRA_LUNCH_ID, -1);


            System.out.println("THIS IS MY LunchID===========" + lunchID);
            if ( lunchID== -1 ) {
//saving user input to database
                LunchEntity lunchEntity = new LunchEntity(title, description, price);
                lunchViewModel1.insert(lunchEntity);
                Toast.makeText(this, "Term Saved!", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == EDIT_LUNCH_REQUEST && resultCode == RESULT_OK)
        {

            String id2 = data.getStringExtra(addEditLunch.EXTRA_LUNCH_ID);

            String title = data.getStringExtra(addEditLunch.EXTRA_LUNCH_TITLE);
            String description = data.getStringExtra(addEditLunch.EXTRA_LUNCH_DESCRIPTION);
            String price = data.getStringExtra(addEditLunch.EXTRA_LUNCH_PRICE);
            LunchEntity lunchEntity = new LunchEntity ( title, description, price);
            lunchEntity.setLunchID(Integer.parseInt(id2));
            lunchViewModel1.update(lunchEntity);

            Toast.makeText(this, "Term Updated!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Term NOT Saved!", Toast.LENGTH_SHORT).show();


        }




    }




    }
