package com.zybooks.inventorytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private EditText textUserName;
    private EditText textPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        textUserName = findViewById(R.id.editTextUserName);
        textPassword = findViewById(R.id.editTextPassword);

        Button buttonLogin = (Button)findViewById(R.id.buttonLogin);
        buttonLogin.setOnClickListener(listener -> confirmLogin());
        Button buttonCreateAcct = (Button)findViewById(R.id.buttonCreateAcct);
        buttonCreateAcct.setOnClickListener(listener -> createAccount());

    }

    // Method to confirm login details
    private void confirmLogin() {
        String userName = textUserName.getText().toString();
        String password = textPassword.getText().toString();

        if (UserDatabase.getInstance(getApplicationContext()).authenticate(userName, password)) {
            Intent intent = new Intent(this, DisplayAllActivity.class);
            startActivity(intent);
        }
        else{
            Toast.makeText(this, "invalid credentials", Toast.LENGTH_LONG).show();
        }
    }

    // Take user to RegistrationActivity to create an account
    private void createAccount() {
        Intent intent = new Intent(this, RegistrationActivity.class);
        startActivity(intent);
    }
}