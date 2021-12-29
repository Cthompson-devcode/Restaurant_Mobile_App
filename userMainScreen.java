package com.example.donnasdiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ComponentName;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class userMainScreen extends AppCompatActivity {
    FloatingActionButton phoneButton;

    FloatingActionButton smsButton;

    FloatingActionButton directionButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_main_screen);


        phoneButton = findViewById(R.id.floatingActionButtonPhone);

        phoneButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:8005555555"));
                startActivity(intent);

            }
        });

        smsButton = findViewById(R.id.floatingActionButtonSMS);

        smsButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(Intent.ACTION_SENDTO);
                intent.setType("vnd.android-dir/mms-sms");
                intent.setData(Uri.parse("sms:8005555555"));
                startActivity(intent);
            }
        });


        directionButton = findViewById(R.id.floatingActionButtonMAP);
        directionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {





                Uri gmmIntentUri = Uri.parse("geo:0,0?q=2467 Cleghorn Street, Honolulu, HI");
                Intent intent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                intent.setComponent(new ComponentName("com.google.android.apps.maps",
                        "com.google.android.maps.MapsActivity"));
                startActivity(intent);
            }
        });



    }










    public void view_menu(View view) {

        Intent intent = new Intent(userMainScreen.this, userMenuSelectionActivity.class);
        startActivity(intent);

    }
}