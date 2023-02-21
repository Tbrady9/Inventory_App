package com.zybooks.timbrady_inventorytracker;

import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder>{
    private Context context;
    private ArrayList itemNum, itemName, itemQty;

    public ItemAdapter(Context context, ArrayList itemNums, ArrayList itemNames, ArrayList itemQtys) {
        this.context = context;
        this.itemNum = itemNums;
        this.itemName = itemNames;
        this.itemQty = itemQtys;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itementry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        // Bind view holder to auto-populate item details on the edit screen (DisplayOneActivity.java)
        holder.itemNum.setText(String.valueOf(itemNum.get(position)));
        holder.itemName.setText(String.valueOf(itemName.get(position)));
        holder.itemQty.setText(String.valueOf(itemQty.get(position)));

        String itemNumStr = holder.itemNum.getText().toString();
        String itemNameStr = holder.itemName.getText().toString();
        String itemQtyStr = holder.itemQty.getText().toString();

        // navigate to each item's update page and display the item details
        holder.itemButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Context context = v.getContext();
                Intent intent = new Intent(context, DisplayOneActivity.class);
                intent.putExtra("itemNumExtra", itemNumStr);
                intent.putExtra("itemNameExtra", itemNameStr);
                intent.putExtra("itemQtyExtra", itemQtyStr);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return itemNum.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        TextView itemNum, itemName, itemQty;
        Button itemButton;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            context = itemView.getContext();
            itemNum = itemView.findViewById(R.id.textViewItemNum);
            itemName = itemView.findViewById(R.id.textViewItemName);
            itemQty = itemView.findViewById(R.id.textViewItemQty);
            itemButton = itemView.findViewById(R.id.buttonUpdateItem);

        }
    }
}

