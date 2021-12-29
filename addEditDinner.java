package com.example.donnasdiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class addEditDinner extends AppCompatActivity {



    public static String EXTRA_DINNER_ID =
            "com.example.donnasdiner.EXTRA_DINNER_ID";


    public static String EXTRA_DINNER_TITLE =
            "com.example.donnasdiner.EXTRA_DINNER_TITLE";

    public static String EXTRA_DINNER_DESCRIPTION =
            "com.example.donnasdiner.EXTRA_DINNER_DESCRIPTION";
    public static String EXTRA_DINNER_PRICE =
            "com.example.donnasdiner.EXTRA_DINNER_PRICE";
    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPrice;
    private EditText editTextID;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_dinner);



        editTextID = findViewById(R.id.textViewDinnerID);
        editTextTitle = findViewById(R.id.edit_text_Dinnertitle);
        editTextDescription = findViewById(R.id.edit_text_DinnerDescription);
        editTextPrice = findViewById(R.id.edit_text_Dinnerprice);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic__cancel);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_DINNER_ID)) {
            setTitle("Update Menu Item -- UPGRADE!");

            //String passedID = intent.getStringExtra(EXTRA_DINNER_ID);
            editTextID.setText(intent.getStringExtra(EXTRA_DINNER_ID));
            editTextPrice.setText(intent.getStringExtra(EXTRA_DINNER_PRICE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DINNER_DESCRIPTION));
            editTextTitle.setText(intent.getStringExtra(EXTRA_DINNER_TITLE));
        } else {
            setTitle("Add another menu item -- Making big moves!");
        }

    }

    private void saveDinner() {

        //String id = EXTRA_DINNER_ID;
        String id = editTextID.getText().toString();
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String price = editTextPrice.getText().toString();


        if (title.trim().isEmpty() || price.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_DINNER_ID, id);
        data.putExtra(EXTRA_DINNER_TITLE, title);
        data.putExtra(EXTRA_DINNER_DESCRIPTION, description);
        data.putExtra(EXTRA_DINNER_PRICE, price);

        //saving edited informtion by linking to ID

        // String idtest = getIntent().getStringExtra(EXTRA_DINNER_ID);
        int id2 = getIntent().getIntExtra(EXTRA_DINNER_ID, -1);
        //   int id2 = Integer.parseInt(idtest);

        System.out.println("THIS IS ID2 --------------" + id2);
        if (id2 != -1) {
            data.putExtra(EXTRA_DINNER_ID, id);
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
                saveDinner();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}