package com.zybooks.inventorytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.TextView;
import android.widget.Toast;

public class RegistrationActivity extends AppCompatActivity {

    EditText userFName, userLName, userName, userRole, userPassword, userPasswordConf;
    Button btnSubmit;
    TextView txtViewConfirm;
    UserDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);

        userName = findViewById(R.id.editTextUserName);
        userFName = findViewById(R.id.editTextFirstName);
        userLName = findViewById(R.id.editTextLastName);
        userPassword = findViewById(R.id.editTextPassword);
        userRole = findViewById(R.id.editTextRole);
        userPasswordConf = findViewById(R.id.editTextPasswordConf);
        txtViewConfirm = findViewById(R.id.textViewConfirm);
        btnSubmit = findViewById(R.id.buttonSubmit);
        db = new UserDatabase(this);

        btnSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String userNameTxt = userName.getText().toString();
                String userFNameTxt = userFName.getText().toString();
                String userLNameTxt = userLName.getText().toString();
                String userPasswordTxt = userPassword.getText().toString();
                String userRoleTxt = userRole.getText().toString();

                Boolean checkAddUser = db.addUser(userNameTxt, userFNameTxt, userLNameTxt,
                                                  userPasswordTxt, userRoleTxt);
                if (checkAddUser == true){
                    txtViewConfirm.setText("User added successfully");
                }
                else {
                    txtViewConfirm.setText("Action failed");
                }
            }
        });
    }
}