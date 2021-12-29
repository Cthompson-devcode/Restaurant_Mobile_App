package com.example.donnasdiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.donnasdiner.Database.restaurantRepository;
import com.example.donnasdiner.Entities.BreakfastEntity;
import com.example.donnasdiner.Entities.DinnerEntity;
import com.example.donnasdiner.Entities.LunchEntity;
import com.example.donnasdiner.Entities.Notes;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        restaurantRepository repository = new restaurantRepository(getApplication());

        BreakfastEntity breakfastEntity = new BreakfastEntity ("Traditional Breakfast", "Traditional breakfast with two eggs, choice of meat (Bacon or Sausage) & grits", "10.50");
        repository.insert(breakfastEntity);
        breakfastEntity = new BreakfastEntity ("Waffles", "Chocolate Chip waffles topped with fresh bananas", "10.00");
       repository.insert(breakfastEntity);


       LunchEntity lunchEntity = new LunchEntity ( "Cuban Sandwich",  "Slow roasted pulled pork with a slice of honey ham topped with a pickle. Mustard spread optional",  "11.00 ");
        repository.insert(lunchEntity);



        DinnerEntity dinnerEntity = new DinnerEntity ("Ribeye Steak", "Seared at 5000 degrees, juicy and delicious - served with a side of mashed potatoes and basalmic brussel sprouts topped with goat cheese", "15.00" );
        repository.insert(dinnerEntity);

        Notes notes = new Notes (2, "Notes", "Notesdescription");
        repository.insert(notes);













    }

    public void gotopage(View view) {
        Intent intent = new Intent(MainActivity.this, userMainScreen.class);
        startActivity(intent);

    }

    public void gotoOwnerpage(View view) {

        Intent intent = new Intent(MainActivity.this, ownerLoginActivity.class);
        startActivity(intent);
    }

    /*
    public void breakfastPage(View view) {
        Intent intent = new Intent(MainActivity.this, breakfastListActivity.class);
        startActivity(intent);

    }

    public void go_to_lunch(View view) {
        Intent intent = new Intent(MainActivity.this, lunchListActivity.class);
        startActivity(intent);
    }

    public void go_to_dinner(View view) {

        Intent intent = new Intent(MainActivity.this, dinnerListActivity.class);
        startActivity(intent);


    }

     */


}