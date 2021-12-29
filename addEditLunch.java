package com.example.donnasdiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class addEditLunch extends AppCompatActivity {

    public static String EXTRA_LUNCH_ID =
            "com.example.donnasdiner.EXTRA_LUNCH_ID";


    public static String EXTRA_LUNCH_TITLE =
            "com.example.donnasdiner.EXTRA_LUNCH_TITLE";

    public static String EXTRA_LUNCH_DESCRIPTION =
            "com.example.donnasdiner.EXTRA_LUNCH_DESCRIPTION";
    public static String EXTRA_LUNCH_PRICE =
            "com.example.donnasdiner.EXTRA_BREAKFAST_PRICE";
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPrice;
    private EditText editTextID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_lunch);

        editTextID = findViewById(R.id.textViewLunchID);
        editTextTitle = findViewById(R.id.edit_text_Lunchtitle);
        editTextDescription = findViewById(R.id.edit_text_LunchDescription);
        editTextPrice = findViewById(R.id.edit_text_Lunchprice);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic__cancel);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_LUNCH_ID)) {
            setTitle("Update Menu Item -- UPGRADE!");

            //String passedID = intent.getStringExtra(EXTRA_LUNCH_ID);
            editTextID.setText(intent.getStringExtra(EXTRA_LUNCH_ID));
            editTextPrice.setText(intent.getStringExtra(EXTRA_LUNCH_PRICE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_LUNCH_DESCRIPTION));
            editTextTitle.setText(intent.getStringExtra(EXTRA_LUNCH_TITLE));
        } else {
            setTitle("Add another menu item -- Making big moves!");
        }

    }

    private void saveLunch() {

        //String id = EXTRA_LUNCH_ID;
        String id = editTextID.getText().toString();
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String price = editTextPrice.getText().toString();


        if (title.trim().isEmpty() || price.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_LUNCH_ID, id);
        data.putExtra(EXTRA_LUNCH_TITLE, title);
        data.putExtra(EXTRA_LUNCH_DESCRIPTION, description);
        data.putExtra(EXTRA_LUNCH_PRICE, price);

        //saving edited informtion by linking to ID


        int id2 = getIntent().getIntExtra(EXTRA_LUNCH_ID, -1);

        System.out.println("THIS IS ID2 --------------" + id2);
        if (id2 != -1) {
            data.putExtra(EXTRA_LUNCH_ID, id);
        }

        setResult(RESULT_OK, data);
        finish();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.save_item, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {

            case R.id.save_Breakfast:
                saveLunch();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}

