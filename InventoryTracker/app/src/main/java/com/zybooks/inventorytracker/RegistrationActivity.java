package com.zybooks.inventorytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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


        // Focus Change Listener on all registration fields to clear confirmation message
        // UserFName field
        userFName.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    txtViewConfirm.setText("");
                }
            }
        });

        // UserLName field
        userLName.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    txtViewConfirm.setText("");
                }
            }
        });

        // UserName field
        userName.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus) {
                    txtViewConfirm.setText("");
                }
            }
        });

        // UserRole field
        userRole.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    txtViewConfirm.setText("");
                }
            }
        });

        // UserPassword field
        userPassword.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    txtViewConfirm.setText("");
                }
            }
        });

        // UserPasswordConf field
        userPasswordConf.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    txtViewConfirm.setText("");
                }
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener(){

            // Checks if passwords match and adds user if they do
            @Override
            public void onClick(View v) {
                if (userPassword == userPasswordConf) {
                    String userNameTxt = userName.getText().toString();
                    String userFNameTxt = userFName.getText().toString();
                    String userLNameTxt = userLName.getText().toString();
                    String userPasswordTxt = userPassword.getText().toString();
                    String userRoleTxt = userRole.getText().toString();

                    Boolean checkAddUser = db.addUser(userNameTxt, userFNameTxt, userLNameTxt,
                            userPasswordTxt, userRoleTxt);
                    if (checkAddUser == true) {
                        txtViewConfirm.setText("User added successfully");
                    } else {
                        txtViewConfirm.setText("Action failed");
                    }
                }
                else {
                    txtViewConfirm.setText("Action Failed");
                }
            }
        });
    }
}