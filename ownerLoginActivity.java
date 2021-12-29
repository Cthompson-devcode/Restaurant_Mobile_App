package com.example.donnasdiner;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class ownerLoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_owner_login);
    }

    public void goToOwnerMenuFromLogin(View view) {

        EditText userName;
        EditText password;
        String stringUsername;
        String stringPassword;

        userName = findViewById(R.id.editText_Username);
        password = findViewById(R.id.editTextTextPassword);


        stringUsername = userName.getText().toString();
        stringPassword = password.getText().toString();

        if (stringPassword.isEmpty() || stringUsername.isEmpty())
        {

            Toast.makeText(this, "Please enter a username and/or password", Toast.LENGTH_SHORT).show();
        }

       else if (!stringUsername.equals("owner"))
        {

            Toast.makeText(this, "UserName is incorrect", Toast.LENGTH_SHORT).show();
            return;
        }

      else  if (!stringPassword.equals("owner"))
        {

            Toast.makeText(this, "Password is incorrect", Toast.LENGTH_SHORT).show();
            return;
        }

      else {
            Intent intent = new Intent(ownerLoginActivity.this, ownerMainScreenActivity.class);
            startActivity(intent);
        }
    }
}