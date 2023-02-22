package com.zybooks.timbrady_inventorytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
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

    TextView textItem;
    String itemNumStr, itemNameStr, itemQtyStr;
    EditText textUpdateQty, textOrderQty;
    Button btnDeleteItem;
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
        textOrderQty = findViewById(R.id.editTextOrderNum);
        Button btnUpdateQty = (Button) findViewById(R.id.buttonUpdateInv);
        Button btnOrderQty = (Button) findViewById(R.id.buttonOrderInv);
        Button btnDeleteItem = (Button) findViewById(R.id.buttonDeleteItem);
        Intent intent2 = new Intent(this, DisplayAllActivity.class);
        btnUpdateQty.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String itemNumTxt = intent.getStringExtra("itemNumExtra");
                String itemNameTxt = intent.getStringExtra("itemNameExtra");
                String itemQtyTxt = textUpdateQty.getText().toString();

                // Check that an item was updated and display confirmation message
                Boolean checkUpdateItem = db.updateItem(itemNumTxt, itemNameTxt, itemQtyTxt);
                if (checkUpdateItem == true) {
                    Toast.makeText(DisplayOneActivity.this, "Quantity updated!", Toast.LENGTH_SHORT).show();
                    startActivity(intent2);
                }
            }
        });

        btnDeleteItem.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // Alert user to confirm delete
                confirmDeleteAlert();
            }
        });

        btnOrderQty.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String orderQty = textOrderQty.getText().toString();
                orderItems(String.valueOf(orderQty), itemNameStr);
            }
        });

        textItem = findViewById(R.id.autoCompleteItem);
        textItem.setText(String.format("Item Number:  %s\nItem Name:  %s\nCurrent Quantity:  %s",
                itemNumStr, itemNameStr, itemQtyStr));

    }

    /**
     * Order more of an item. This is not intended to change anything on the screen
     * since the item has not been received yet.
     */
    private void orderItems(String orderQty, String itemName){
        Toast.makeText(this, orderQty + " " + itemName + " ordered", Toast.LENGTH_LONG).show();
    }

    // Alert user that they are about to delete an item
    private void confirmDeleteAlert(){
        Intent intent = getIntent();
        Intent intent2 = new Intent(this, DisplayAllActivity.class);

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Delete this item?");
        builder.setMessage("Are you sure you want to delete " + itemNameStr + "?");
        builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String itemNumTxt = intent.getStringExtra("itemNumExtra");

                // Check that an item was deleted and display a confirmation message
                Boolean checkUpdateItem = db.deleteItem(itemNumTxt);
                if (checkUpdateItem == true) {
                    Toast.makeText(DisplayOneActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
                    startActivity(intent2);
                }
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Toast.makeText(DisplayOneActivity.this, "Item not deleted", Toast.LENGTH_SHORT).show();
            }
        });

        builder.create().show();
    }
}