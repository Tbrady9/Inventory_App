package com.zybooks.inventorytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class DisplayOneActivity extends AppCompatActivity {

    TextView textItem, textView2;
    String itemNumStr, itemNameStr, itemQtyStr;
    EditText textUpdateQty, textOrderQty;
    Button  btnOrderQty, btnDeleteItem;
    ItemDatabase db;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_one);

        Intent intent = getIntent();
        itemNumStr = intent.getStringExtra("itemNumExtra");
        itemNameStr = intent.getStringExtra("itemNameExtra");
        itemQtyStr = intent.getStringExtra("itemQtyExtra");
        db = new ItemDatabase(this);
        textUpdateQty = findViewById(R.id.editTextQuantityNum);
        Button btnUpdateQty = (Button) findViewById(R.id.buttonUpdateInv);
        btnUpdateQty.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String itemNumTxt = intent.getStringExtra("itemNumExtra");
                String itemNameTxt = intent.getStringExtra("itemNameExtra");
                String itemQtyTxt = textUpdateQty.getText().toString();


                Boolean checkUpdateItem = db.updateItem(itemNumTxt, itemNameTxt, itemQtyTxt);
                if (checkUpdateItem == true) {
                    Toast.makeText(DisplayOneActivity.this, "Quantity updated!", Toast.LENGTH_SHORT).show();
                }
            }
        });
        //textOrderQty = findViewById(R.id.editTextOrderNum);


        textItem = findViewById(R.id.autoCompleteItem);
        textItem.setText(String.format("Item Number:  %s\nItem Name:  %s\nCurrent Quantity:  %s",
                                        itemNumStr, itemNameStr, itemQtyStr));
    }
/*
    // Method to confirm item qty was updated
    private void confirmUpdateItem() {
        String newItemQty = textUpdateQty.getText().toString();

        if (ItemDatabase.getInstance(getApplicationContext()).updateItem(itemNameStr, itemNameStr, newItemQty)) {
            Toast.makeText(this, "OK", Toast.LENGTH_LONG).show();
            Intent intent = new Intent(this, DisplayAllActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, "nope", Toast.LENGTH_LONG).show();
        }
    }
*/
}