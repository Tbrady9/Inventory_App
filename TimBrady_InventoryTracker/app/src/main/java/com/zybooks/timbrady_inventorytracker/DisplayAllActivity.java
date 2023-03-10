package com.zybooks.timbrady_inventorytracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

public class DisplayAllActivity extends AppCompatActivity {

    private ItemDatabase db;
    private RecyclerView rv;
    private ArrayList<String> itemNum, itemName, itemQty;
    private ItemAdapter adapter;
    private TextView textNoItems;
    private final int NOTIFICATION_ID = 0;
    private static final String sharedPrefs = "sharedPrefs";
    private static final String TOGGLEBUTTON = "togglebutton";
    private static final String EDITTEXT = "userLowQty";
    private Boolean toggleOnOff;
    private String lowQty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayall);
        Button buttonAddItem = findViewById(R.id.buttonAddItem);
        buttonAddItem.setOnClickListener(listener -> addItem());
        Button buttonSettings = findViewById(R.id.buttonSettings);
        buttonSettings.setOnClickListener(listener -> settings());

        db = new ItemDatabase(this);
        itemNum = new ArrayList<>();
        itemName = new ArrayList<>();
        itemQty = new ArrayList<>();
        rv = findViewById(R.id.recyclerView);
        adapter = new ItemAdapter(this, itemNum, itemName, itemQty);
        rv.setAdapter(adapter);
        rv.setLayoutManager(new LinearLayoutManager(this));
        textNoItems = findViewById(R.id.textViewNoItems);

        displayItemData();

        loadData();

        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefs, MODE_PRIVATE);
        toggleOnOff = sharedPreferences.getBoolean(TOGGLEBUTTON, false);

        // Create notification and send if permission granted
        Cursor cursor = db.getItemData();
        if (toggleOnOff) {
            for (int i = 0; i < itemQty.size(); i++) {
               if (Integer.parseInt(itemQty.get(i))  <= Integer.parseInt(String.valueOf(lowQty))) {
                   Notification notification = new NotificationCompat.Builder(DisplayAllActivity.this, CHANNEL_ID_QTY)
                           .setSmallIcon(R.drawable.cog_wheel)
                           .setContentTitle(getString(R.string.app_name))
                           .setContentText("Low quantity! Item Name: " + itemName.get(i))
                           .setPriority(NotificationCompat.PRIORITY_HIGH)
                           .build();

                   NotificationManagerCompat notificationManager = NotificationManagerCompat.from(DisplayAllActivity.this);
                   // Post notification using ID
                   notificationManager.notify(NOTIFICATION_ID, notification);
               }
            }
        }


    }  // End onCreate


    private void addItem() {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

    private void settings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    // Displaying items from the item table into a recycler view
    private void displayItemData(){
        Cursor cursor = db.getItemData();
        if(cursor.getCount()==0){
            textNoItems.setText("No items to display");
            return;
        }
        else{
            textNoItems.setText(null);
            while (cursor.moveToNext()){
                itemNum.add(cursor.getString(0));
                itemName.add(cursor.getString(1));
                itemQty.add(cursor.getString(2));
            }
        }
    }


    private final String CHANNEL_ID_QTY = "channel_qty";
    // Create notification channel
    private void createItemQtyNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel(CHANNEL_ID_QTY, name, importance);
            channel.setDescription(description);

            // Register channel with system
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }

    // Loading shared preferences for toggle button state
    public void loadData(){
        SharedPreferences sharedPreferences = getSharedPreferences(sharedPrefs, MODE_PRIVATE);
        toggleOnOff = sharedPreferences.getBoolean(TOGGLEBUTTON, false);
        lowQty = sharedPreferences.getString(EDITTEXT, "0");
    }
}