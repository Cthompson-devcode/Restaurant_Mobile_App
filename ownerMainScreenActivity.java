package com.example.donnasdiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ownerMainScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_main_screen);
    }

    public void breakfastPage1(View view) {


        Intent intent = new Intent(ownerMainScreenActivity.this, breakfastListActivity.class);
        startActivity(intent);
    }

    public void go_to_lunch1(View view) {


        Intent intent = new Intent(ownerMainScreenActivity.this, lunchListActivity.class);
        startActivity(intent);
    }

    public void go_to_dinner1(View view) {

        Intent intent = new Intent(ownerMainScreenActivity.this, dinnerListActivity.class);
        startActivity(intent);

    }
}