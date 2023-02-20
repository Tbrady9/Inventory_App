package com.zybooks.inventorytracker;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class AddItemActivity extends AppCompatActivity {

    EditText itemNum, itemName, itemQty;
    Button buttonAddSubmit;
    TextView txtViewConfirm;
    ItemDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_item);

        itemNum = findViewById(R.id.editTextItemNum);
        itemName = findViewById(R.id.editTextItemName);
        itemQty = findViewById(R.id.editTextQty);
        buttonAddSubmit = findViewById(R.id.buttonAddSubmit);
        txtViewConfirm = findViewById(R.id.textViewConfirm);
        db = new ItemDatabase(this);

        buttonAddSubmit.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                String itemNumTxt = itemNum.getText().toString();
                String itemNameTxt = itemName.getText().toString();
                String itemQtyTxt = itemQty.getText().toString();

                Boolean checkAddItem = db.addItem(itemNumTxt, itemNameTxt, itemQtyTxt);
                if (checkAddItem == true){
                    txtViewConfirm.setText("Item added successfully");
                }
                else {
                    txtViewConfirm.setText("Action failed");
                }
            }
        });
    }
}