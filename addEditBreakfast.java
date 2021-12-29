package com.example.donnasdiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.donnasdiner.Entities.BreakfastEntity;

import org.w3c.dom.Text;

import java.util.List;

public class addEditBreakfast extends AppCompatActivity {

    public static  String EXTRA_BREAKFAST_ID =
            "com.example.donnasdiner.EXTRA_BREAKFAST_ID";

    public static  String EXTRA_BREAKFAST_TITLE =
            "com.example.donnasdiner.EXTRA_BREAKFAST_TITLE";

    public static  String EXTRA_BREAKFAST_DESCRIPTION =
            "com.example.donnasdiner.EXTRA_BREAKFAST_DESCRIPTION";
    public static  String EXTRA_BREAKFAST_PRICE =
            "com.example.donnasdiner.EXTRA_BREAKFAST_PRICE";

    private EditText editTextTitle;
    private EditText editTextDescription;
    private EditText editTextPrice;
    private EditText editTextID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_edit_breakfast);
            editTextID = findViewById(R.id.textViewBreakfastID);
            editTextTitle = findViewById(R.id.edit_text_Breakfasttitle);
            editTextDescription = findViewById(R.id.edit_text_BreakfastDescription);
            editTextPrice = findViewById(R.id.edit_text_breakfastprice);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic__cancel);

        Intent intent = getIntent();

        if (intent.hasExtra(EXTRA_BREAKFAST_ID))
        {
            setTitle("Update Menu Item -- UPGRADE!");

            //String passedID = intent.getStringExtra(EXTRA_BREAKFAST_ID);
            editTextID.setText(intent.getStringExtra(EXTRA_BREAKFAST_ID));
            editTextPrice.setText(intent.getStringExtra(EXTRA_BREAKFAST_PRICE));
            editTextDescription.setText(intent.getStringExtra(EXTRA_BREAKFAST_DESCRIPTION));
            editTextTitle.setText(intent.getStringExtra(EXTRA_BREAKFAST_TITLE));
        }
        else
        {
            setTitle("Add another menu item -- Making big moves!");
        }

    }

    private void saveBreakfast() {

        //String id = EXTRA_BREAKFAST_ID;
       String id = editTextID.getText().toString();
        String title = editTextTitle.getText().toString();
        String description = editTextDescription.getText().toString();
        String price = editTextPrice.getText().toString();


        if (title.trim().isEmpty() || price.trim().isEmpty() || description.trim().isEmpty()) {
            Toast.makeText(this, "Please complete all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_BREAKFAST_ID, id);
        data.putExtra(EXTRA_BREAKFAST_TITLE, title);
        data.putExtra(EXTRA_BREAKFAST_DESCRIPTION, description);
        data.putExtra(EXTRA_BREAKFAST_PRICE, price);

        int   id2 = getIntent().getIntExtra(EXTRA_BREAKFAST_ID, -1);


        System.out.println("THIS IS ID2 --------------" + id2);
        if (id2 != -1  ) {
            data.putExtra(EXTRA_BREAKFAST_ID, id);
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
                saveBreakfast();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }



}