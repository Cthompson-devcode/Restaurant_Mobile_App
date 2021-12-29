package com.example.donnasdiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.widget.EditText;

public class checkoutActivity extends AppCompatActivity {

    public static final String EXTRA_TOTAL_TAXES =   "111asdfasdfasdfasdfasdf.00;";
    public static final String EXTRA_SUBTOTAL = "1111sdfasdfasdfsdaf.00;" ;
    public static final String EXTRA_TOTAL_ORDER_FINAL = "111111111dddddd.00;";
    public static String EXTRA_TOTAL_PRICE =
            "11111111111.00;";

    private EditText editTextCartPrice;
    private EditText editTextTaxes;
    private EditText editTextSubtotal;
    private EditText editTextDiscountCode;
    private EditText editTextorderTotal;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic__cancel);

       editTextCartPrice = findViewById(R.id.editTextcartTotal);
       editTextTaxes = findViewById(R.id.editTextEstimatedTaxes);
       editTextSubtotal = findViewById(R.id.editTextSubtotal);
       editTextDiscountCode = findViewById(R.id.editTextDiscountCode);
       editTextorderTotal = findViewById(R.id.editTextCompleteOrderTotal);

        Intent intent = getIntent();

        editTextCartPrice.setText(intent.getStringExtra(String.valueOf(EXTRA_TOTAL_PRICE)));
        editTextTaxes.setText(intent.getStringExtra(EXTRA_TOTAL_TAXES));
        editTextSubtotal.setText(intent.getStringExtra(EXTRA_SUBTOTAL));
        editTextorderTotal.setText(intent.getStringExtra(EXTRA_TOTAL_ORDER_FINAL));







System.out.println("THIS IS MY TOTAL PRICE _---------" + EXTRA_TOTAL_PRICE);

    }
}