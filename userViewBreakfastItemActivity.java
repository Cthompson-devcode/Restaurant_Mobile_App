package com.example.donnasdiner;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.donnasdiner.DAO.cartDAO;
import com.example.donnasdiner.Database.restaurantRepository;
import com.example.donnasdiner.Entities.BreakfastEntity;
import com.example.donnasdiner.Entities.cartEntity;
import com.example.donnasdiner.ViewModel.cartViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Objects;
import java.util.concurrent.ExecutionException;

public class userViewBreakfastItemActivity extends AppCompatActivity {


    public static String EXTRA_BREAKFAST_ID =
            "com.example.donnasdiner.EXTRA_BREAKFAST_ID";

    public static String EXTRA_BREAKFAST_TITLE =
            "com.example.donnasdiner.EXTRA_BREAKFAST_TITLE";

    public static String EXTRA_BREAKFAST_DESCRIPTION =
            "com.example.donnasdiner.EXTRA_BREAKFAST_DESCRIPTION";
    public static String EXTRA_BREAKFAST_PRICE =
            "com.example.donnasdiner.EXTRA_BREAKFAST_PRICE";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPrice;
    private EditText editTextID;

    private cartViewModel cartViewModel;
    private restaurantRepository restaurantRepository = new restaurantRepository(getApplication());


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_view_food_item);
        Objects.requireNonNull(getSupportActionBar()).setHomeAsUpIndicator(R.drawable.ic__cancel);

        editTextID = findViewById(R.id.textViewuserFoodID);
        editTextTitle = findViewById(R.id.edit_text_userFoodTitle);
        editTextDescription = findViewById(R.id.edit_text_userDescription);
        editTextPrice = findViewById(R.id.edit_text_userprice);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic__cancel);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_BREAKFAST_ID)) {
            setTitle("SELECTED ITEM");

            //String passedID = intent.getStringExtra(EXTRA_BREAKFAST_ID);
            editTextID.setText(intent.getStringExtra(EXTRA_BREAKFAST_ID));
            editTextPrice.setText(intent.getStringExtra(EXTRA_BREAKFAST_PRICE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_BREAKFAST_DESCRIPTION));
            editTextTitle.setText(intent.getStringExtra(EXTRA_BREAKFAST_TITLE));
        }

        FloatingActionButton add_to_cart = findViewById(R.id.button_add_breakfast_to_cart);
        add_to_cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                    int dishID = Integer.parseInt(editTextID.getText().toString());
                    Double dishPrice = Double.parseDouble(editTextPrice.getText().toString());
                    String dishName = editTextTitle.getText().toString();

                    cartEntity cartEntity = new cartEntity(dishName, dishPrice, dishID);
                    restaurantRepository.insert(cartEntity);


            }
        });



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
                Intent intent = new Intent(userViewBreakfastItemActivity.this, addtoCartActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }


}