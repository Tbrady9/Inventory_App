package com.zybooks.inventorytracker;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;

public class DisplayAllActivity extends AppCompatActivity {

    private EditText textItemSearch;
    private ItemDatabase db;
    private RecyclerView rv;
    private ArrayList<String> itemNum, itemName, itemQty;
    private ItemAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displayall);

        textItemSearch = findViewById(R.id.editTextItemSearch);

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
        displayItemData();
    }

    private void addItem() {
        Intent intent = new Intent(this, AddItemActivity.class);
        startActivity(intent);
    }

    private void settings() {
        Intent intent = new Intent(this, SettingsActivity.class);
        startActivity(intent);
    }

    private void displayItemData(){
        Cursor cursor = db.getItemData();
        if(cursor.getCount()==0){
            Toast.makeText(DisplayAllActivity.this, "No items found", Toast.LENGTH_LONG).show();
            return;
        }
        else{
            while (cursor.moveToNext()){
                itemNum.add(cursor.getString(0));
                itemName.add(cursor.getString(1));
                itemQty.add(cursor.getString(2));
            }
        }
    }
}