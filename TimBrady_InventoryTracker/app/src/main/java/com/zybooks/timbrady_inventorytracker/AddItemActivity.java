package com.zybooks.timbrady_inventorytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddItemActivity extends AppCompatActivity {

    EditText itemName, itemQty;
    Button buttonAddSubmit;
    TextView txtViewConfirm;
    ItemDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemName = findViewById(R.id.editTextItemName);
        itemQty = findViewById(R.id.editTextQty);
        buttonAddSubmit = findViewById(R.id.buttonAddSubmit);
        txtViewConfirm = findViewById(R.id.textViewConfirm);
        db = new ItemDatabase(this);
        Intent intent = new Intent(this, DisplayAllActivity.class);
        buttonAddSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String itemNameTxt = itemName.getText().toString();
                String itemQtyTxt = itemQty.getText().toString();

                // Check if item was added
                Boolean checkAddItem = db.addItem(itemNameTxt, itemQtyTxt);
                if (checkAddItem == true){
                    startActivity(intent);
                }
                else {
                    txtViewConfirm.setText("Action failed");
                }
            }
        });
    }
}