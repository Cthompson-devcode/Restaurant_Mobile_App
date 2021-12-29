package com.example.donnasdiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.example.donnasdiner.Database.restaurantRepository;
import com.example.donnasdiner.Entities.cartEntity;
import com.example.donnasdiner.ViewModel.cartViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;

public class viewOrderDetailsActivity extends AppCompatActivity {

    public static String EXTRA_BREAKFAST_ID =
            "com.example.donnasdiner.EXTRA_BREAKFAST_ID";

    public static String EXTRA_BREAKFAST_TITLE =
            "com.example.donnasdiner.EXTRA_BREAKFAST_TITLE";

    public static String EXTRA_BREAKFAST_DESCRIPTION =
            "com.example.donnasdiner.EXTRA_BREAKFAST_DESCRIPTION";
    public static String EXTRA_BREAKFAST_PRICE =
            "com.example.donnasdiner.EXTRA_BREAKFAST_PRICE";

    public static String EXTRA_CART_ID = "com.example.donnasdiner.EXTRA_CART_ID";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPrice;
    private EditText editTextID;

    private com.example.donnasdiner.ViewModel.cartViewModel cartViewModel;
    private com.example.donnasdiner.Database.restaurantRepository restaurantRepository = new restaurantRepository(getApplication());

    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_order_details);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic__cancel);

        editTextID = findViewById(R.id.textViewBreakfastID);
        editTextTitle = findViewById(R.id.edit_text_Breakfasttitle);
        editTextDescription = findViewById(R.id.edit_text_BreakfastDescription);
        editTextPrice = findViewById(R.id.edit_text_breakfastprice);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic__cancel);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_BREAKFAST_ID)) {
            setTitle("MENU ITEM");


            editTextID.setText(intent.getStringExtra(EXTRA_BREAKFAST_ID));
            editTextPrice.setText(intent.getStringExtra(EXTRA_BREAKFAST_PRICE));

            editTextTitle.setText(intent.getStringExtra(EXTRA_BREAKFAST_TITLE));

            id = Integer.parseInt(editTextID.getText().toString());
        }


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.check_out, menu);

        return true;
    }

   @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.check_out_menu:
                Intent intent = new Intent(viewOrderDetailsActivity.this, addtoCartActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }



    }


    public void go_to_add_note(View view) {



        Intent intent = new Intent(viewOrderDetailsActivity.this, noteOrderList.class);
        intent.putExtra(String.valueOf(noteOrderList.EXTRA_CART_ID), id);
        System.out.println("THIS IS MY CART ID IN VIEW ORDER DETAILS getting passed -------" + id);
        startActivity(intent);
    }
}