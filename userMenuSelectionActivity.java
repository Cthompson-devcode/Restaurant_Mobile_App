package com.example.donnasdiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class userMenuSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_menu_selection);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic__cancel);
    }


    public void go_to_lunch1(View view) {


        Intent intent = new Intent(userMenuSelectionActivity.this, userLunchActivity.class);
        startActivity(intent);
    }

    public void go_to_dinner1(View view) {

        Intent intent = new Intent(userMenuSelectionActivity.this, userDinnerActivity.class);
        startActivity(intent);

    }

    public void userbreakfastPage(View view) {
        Intent intent = new Intent(userMenuSelectionActivity.this, userBreakfastList.class);
        startActivity(intent);

    }
}