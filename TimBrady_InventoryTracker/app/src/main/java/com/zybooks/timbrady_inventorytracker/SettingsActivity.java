package com.zybooks.timbrady_inventorytracker;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.Manifest;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;


public class SettingsActivity extends AppCompatActivity {
    private int NOTIFICATION_PERMISSION_CODE = 1;
    private ToggleButton toggleButton;
    private EditText userLowQty;
    private static final String sharedPrefs = "sharedPrefs";
    private static final String TOGGLEBUTTON = "togglebutton";
    private static final String EDITTEXT = "userLowQty";
    Boolean toggleOnOff;
    private String lowQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        toggleButton = findViewById(R.id.toggleButtonAllowNotif);
        userLowQty = findViewById(R.id.editTextNumber);
        Button btnSaveSettings = findViewById(R.id.buttonSubmitSettings);

        btnSaveSettings.setOnClickListener(listener -> displayAll());

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveData();
            }
        });

        // Listener to check user input for low qty target and save to preferences
        userLowQty.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                checkAndSetUserLowQty();
                saveData();
            }
        });

        // Check current permission status and request permission if needed
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (toggleButton.isChecked()) {
                    if (ContextCompat.checkSelfPermission(SettingsActivity.this,
                            Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                                requestNotificationPermission();
                    }
                }
            }
        });

        loadData();
        updateViews();
    }    // End OnCreate

    // Requesting notification permission
    private void requestNotificationPermission() {
        ToggleButton toggleButton = findViewById(R.id.toggleButtonAllowNotif);
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                android.Manifest.permission.POST_NOTIFICATIONS)) {

            new AlertDialog.Builder(this)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to allow notification of items with low quantity")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            ActivityCompat.requestPermissions(SettingsActivity.this,
                                    new String[] {android.Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_CODE);
                            toggleButton.setChecked(true);
                        }
                    })
                    .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                            toggleButton.setChecked(false);
                        }
                    })
                    .create()
                    .show();
        } else {
            ActivityCompat.requestPermissions(this,
                    new String[] {Manifest.permission.POST_NOTIFICATIONS}, NOTIFICATION_PERMISSION_CODE);
        }
    }

    // Toast message for result of permission request
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == NOTIFICATION_PERMISSION_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Toast.makeText(this, "Permission granted", Toast.LENGTH_LONG).show();
            } else {
                Toast.makeText(this, "Permission denied", Toast.LENGTH_LONG).show();
            }
        }
    }

    // Saving data to shared preferences to save toggle button state
    public void saveData(){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefs, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putBoolean(TOGGLEBUTTON, toggleButton.isChecked());
        editor.putString(EDITTEXT, userLowQty.getText().toString());
        editor.apply();
    }

    // Loading shared preferences for toggle button state
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefs, MODE_PRIVATE);
        toggleOnOff = sharedPreferences.getBoolean(TOGGLEBUTTON, false);
        lowQty = sharedPreferences.getString(EDITTEXT, "0");
    }

    // Setting toggle button state based on shared preference
    public void updateViews(){
        toggleButton.setChecked(toggleOnOff);
        userLowQty.setText(lowQty);
    }

    // Check for null value, set to 0,  and warn the user
    private void checkAndSetUserLowQty(){
        if (userLowQty.getText().toString() == "") {
            userLowQty.setText("0");
            Toast.makeText(this, "Please enter a number", Toast.LENGTH_SHORT).show();
        }
    }

    // Navigate back to displayAll
    private void displayAll() {
        Intent intent = new Intent(this, DisplayAllActivity.class);
        startActivity(intent);
    }
}