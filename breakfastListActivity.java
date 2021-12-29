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

import com.example.donnasdiner.Adapter.BreakfastAdapter;
import com.example.donnasdiner.Entities.BreakfastEntity;
import com.example.donnasdiner.ViewModel.breakfastViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.example.donnasdiner.Database.restaurantRepository;

import java.net.URISyntaxException;
import java.util.List;

public class breakfastListActivity extends AppCompatActivity {

    public static final int ADD_BREAKFAST_REQUEST = 1;
    public static final int EDIT_BREAKFAST_REQUEST = 2;
    public static  String EXTRA_BREAKFAST_ID =
            "com.example.donnasdiner.EXTRA_BREAKFAST_ID";

    private restaurantRepository restaurantRepository;
    private breakfastViewModel breakfastViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_breakfast_list);


        setTitle("Breakfast Menu");
        FloatingActionButton buttonAddBreakfast = findViewById(R.id.button_add_breakfast);
        buttonAddBreakfast.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent (breakfastListActivity.this, addEditBreakfast.class);
                startActivityForResult(intent, ADD_BREAKFAST_REQUEST );

                intent.putExtra(addEditBreakfast.EXTRA_BREAKFAST_ID, String.valueOf(-1));
            }
        });
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        BreakfastAdapter adapter = new BreakfastAdapter();
        recyclerView.setAdapter(adapter);


        breakfastViewModel = ViewModelProviders.of(this).get(breakfastViewModel.class);
        breakfastViewModel.getAllBreakfast().observe(this, new Observer<List<BreakfastEntity>>() {
            @Override
            public void onChanged(@Nullable List<BreakfastEntity> breakfast) {

                adapter.setBreakfast(breakfast);
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

                BreakfastEntity breakfastEntity = adapter.getBreakfastAt(viewHolder.getAdapterPosition());
                breakfastViewModel breakfastViewModel = null;
                try {
                    breakfastViewModel = new breakfastViewModel(getApplication());
                } catch (URISyntaxException e) {
                    e.printStackTrace();
                }


                breakfastViewModel.delete(adapter.getBreakfastAt(viewHolder.getAdapterPosition()));

                    Toast.makeText(breakfastListActivity.this, "Breakfast Item Deleted! ", Toast.LENGTH_SHORT).show();
                }


        }).attachToRecyclerView(recyclerView);

        adapter.setonItemClickListener(new BreakfastAdapter.onItemClickListener() {
            @Override

            public void onItemClick(BreakfastEntity breakfastEntity) {
                //sending data that was clicked


            }
        });

        adapter.setonItemClickListener(new BreakfastAdapter.onItemClickListener() {
            @Override

            public void onItemClick(BreakfastEntity breakfastEntity) {
                //sending data that was clicked
                Intent intent = new Intent(breakfastListActivity.this, addEditBreakfast.class);
                intent.putExtra(addEditBreakfast.EXTRA_BREAKFAST_ID, String.valueOf(breakfastEntity.getBreakfastID()));
                    intent.putExtra(addEditBreakfast.EXTRA_BREAKFAST_TITLE, breakfastEntity.getBreakfastTitle());
                intent.putExtra(addEditBreakfast.EXTRA_BREAKFAST_DESCRIPTION, breakfastEntity.getBreakfastDetails());
                intent.putExtra(addEditBreakfast.EXTRA_BREAKFAST_PRICE, breakfastEntity.getBreakfastPrice());
                startActivityForResult(intent, EDIT_BREAKFAST_REQUEST);

            }
        });




    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        System.out.println("THIS IS MY REQUEST CODE+++++++++++==" + requestCode);

        if (requestCode == ADD_BREAKFAST_REQUEST && resultCode == RESULT_OK)
        {
            String title = data.getStringExtra(addEditBreakfast.EXTRA_BREAKFAST_TITLE);
            String description = data.getStringExtra(addEditBreakfast.EXTRA_BREAKFAST_DESCRIPTION);
            String price = data.getStringExtra(addEditBreakfast.EXTRA_BREAKFAST_PRICE);
            int breakfastID = data.getIntExtra(addEditBreakfast.EXTRA_BREAKFAST_ID, -1);


            System.out.println("THIS IS MY BREAKFASTID===========" + breakfastID);
            if ( breakfastID== -1 ) {
//saving user input to database
                BreakfastEntity breakfastEntity = new BreakfastEntity(title, description, price);
                breakfastViewModel.insert(breakfastEntity);
                Toast.makeText(this, "Term Saved!", Toast.LENGTH_SHORT).show();
            }
        }
        else if (requestCode == EDIT_BREAKFAST_REQUEST && resultCode == RESULT_OK)
        {

            String id2 = data.getStringExtra(addEditBreakfast.EXTRA_BREAKFAST_ID);
           String stringID = String.valueOf(id2);
          //  int id = Integer.parseInt(stringID);

//           int id = data.getIntExtra(addEditBreakfast.EXTRA_BREAKFAST_ID, -1);

            if ( stringID.equals("-1"))
            {
                Toast.makeText(this, " Cannot Update Term", Toast.LENGTH_SHORT).show();
                return;

            }


            String title = data.getStringExtra(addEditBreakfast.EXTRA_BREAKFAST_TITLE);
            String description = data.getStringExtra(addEditBreakfast.EXTRA_BREAKFAST_DESCRIPTION);
            String price = data.getStringExtra(addEditBreakfast.EXTRA_BREAKFAST_PRICE);
           BreakfastEntity breakfastEntity = new BreakfastEntity ( title, description, price);
            breakfastEntity.setBreakfastID(Integer.parseInt(id2));
            breakfastViewModel.update(breakfastEntity);

            Toast.makeText(this, "Term Updated!", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(this, "Term NOT Saved!", Toast.LENGTH_SHORT).show();


        }




    }


    }
