package com.zybooks.inventorytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.TextView;

public class DisplayOneActivity extends AppCompatActivity {

    TextView textItem;
    String itemNumStr, itemNameStr, itemQtyStr;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_one);

        Intent intent = getIntent();
        itemNumStr = intent.getStringExtra("itemNumExtra");
        itemNameStr = intent.getStringExtra("itemNameExtra");
        itemQtyStr = intent.getStringExtra("itemQtyExtra");

        textItem = findViewById(R.id.autoCompleteItem);
        textItem.setText("Item Number: " + itemNumStr + "\n" +
                        "Item Name: " + itemNameStr + "\n" +
                        "Current Quantity: " + itemQtyStr);
    }

}