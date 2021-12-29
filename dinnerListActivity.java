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

import com.example.donnasdiner.Adapter.DinnerAdapter;
import com.example.donnasdiner.Adapter.LunchAdapter;
import com.example.donnasdiner.Entities.DinnerEntity;
import com.example.donnasdiner.Entities.LunchEntity;
import com.example.donnasdiner.ViewModel.DinnerViewModel;
import com.example.donnasdiner.ViewModel.LunchViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class dinnerListActivity extends AppCompatActivity {




    public static final int ADD_DINNER_REQUEST = 1;
    public static final int EDIT_DINNER_REQUEST = 2;
    public static  String EXTRA_DINNER_ID =
            "com.example.donnasdiner.EXTRA_LUNCH_ID";

    private com.example.donnasdiner.Database.restaurantRepository restaurantRepository;
    private DinnerViewModel dinnerViewModel1;

    //private LunchViewModel lunchViewModel;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dinner_list);




            setTitle("Dinner Menu");
            FloatingActionButton buttonAddDinner = findViewById(R.id.button_add_breakfast);
            buttonAddDinner.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent (dinnerListActivity.this, addEditDinner.class);
                    startActivityForResult(intent, ADD_DINNER_REQUEST);

                    intent.putExtra(addEditDinner.EXTRA_DINNER_ID, String.valueOf(-1));
                }
            });
            RecyclerView recyclerView = findViewById(R.id.recycler_view);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setHasFixedSize(true);

            DinnerAdapter adapter = new DinnerAdapter();
            recyclerView.setAdapter(adapter);


            dinnerViewModel1 = ViewModelProviders.of(this).get(DinnerViewModel.class);
            dinnerViewModel1.getAllDinner().observe(this, new Observer<List<DinnerEntity>>() {
                @Override
                public void onChanged(@Nullable List<DinnerEntity> dinner) {

                    adapter.setDinner(dinner);
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

                    DinnerEntity dinnerEntity = adapter.getDinnerAt(viewHolder.getAdapterPosition());
                    DinnerViewModel dinnerViewModel = new DinnerViewModel(getApplication());




                    dinnerViewModel.delete(adapter.getDinnerAt(viewHolder.getAdapterPosition()));

                    Toast.makeText(dinnerListActivity.this, "Dinner Item Deleted! ", Toast.LENGTH_SHORT).show();
                }


            }).attachToRecyclerView(recyclerView);

            adapter.setonItemClickListener(new DinnerAdapter.onItemClickListener() {
                @Override

                public void onItemClick(DinnerEntity dinnerEntity) {
                    //sending data that was clicked


                }
            });

            adapter.setonItemClickListener(new DinnerAdapter.onItemClickListener() {
                @Override

                public void onItemClick(DinnerEntity dinnerEntity) {
                    //sending data that was clicked
                    Intent intent = new Intent(dinnerListActivity.this, addEditDinner.class);
                    intent.putExtra(addEditDinner.EXTRA_DINNER_ID, String.valueOf(dinnerEntity.getDinnerID()));
                    intent.putExtra(addEditDinner.EXTRA_DINNER_TITLE, dinnerEntity.getdinnerTitle());
                    intent.putExtra(addEditDinner.EXTRA_DINNER_DESCRIPTION, dinnerEntity.getdinnerDetails());
                    intent.putExtra(addEditDinner.EXTRA_DINNER_PRICE, dinnerEntity.getdinnerPrice());
                    startActivityForResult(intent, EDIT_DINNER_REQUEST);

                }
            });




        }

        @Override
        protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
            super.onActivityResult(requestCode, resultCode, data);

            System.out.println("THIS IS MY REQUEST CODE+++++++++++==" + requestCode);

            if (requestCode == ADD_DINNER_REQUEST && resultCode == RESULT_OK)
            {
                String title = data.getStringExtra(addEditDinner.EXTRA_DINNER_TITLE);
                String description = data.getStringExtra(addEditDinner.EXTRA_DINNER_DESCRIPTION);
                String price = data.getStringExtra(addEditDinner.EXTRA_DINNER_PRICE);
                int dinnerID = data.getIntExtra(addEditDinner.EXTRA_DINNER_ID, -1);


                System.out.println("THIS IS MY DinnerID===========" + dinnerID);
                if ( dinnerID== -1 ) {
//saving user input to database
                    DinnerEntity dinnerEntity = new DinnerEntity(title, description, price);
                    dinnerViewModel1.insert(dinnerEntity);
                    Toast.makeText(this, "Term Saved!", Toast.LENGTH_SHORT).show();
                }
            }
            else if (requestCode == EDIT_DINNER_REQUEST && resultCode == RESULT_OK)
            {

                String id2 = data.getStringExtra(addEditDinner.EXTRA_DINNER_ID);

                String title = data.getStringExtra(addEditDinner.EXTRA_DINNER_TITLE);
                String description = data.getStringExtra(addEditDinner.EXTRA_DINNER_DESCRIPTION);
                String price = data.getStringExtra(addEditDinner.EXTRA_DINNER_PRICE);
                DinnerEntity dinnerEntity = new DinnerEntity ( title, description, price);
                dinnerEntity.setDinnerID(Integer.parseInt(id2));
                dinnerViewModel1.update(dinnerEntity);

                Toast.makeText(this, "Term Updated!", Toast.LENGTH_SHORT).show();
            }
            else
            {
                Toast.makeText(this, "Term NOT Saved!", Toast.LENGTH_SHORT).show();


            }




        }




    }
