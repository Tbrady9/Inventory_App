package com.zybooks.inventorytracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ItemAdapter extends RecyclerView.Adapter<ItemAdapter.MyViewHolder>{
    private Context context;
    private ArrayList itemNum, itemName, itemQty;

    public ItemAdapter(Context context, ArrayList itemNum, ArrayList itemName, ArrayList itemQty) {
        this.context = context;
        this.itemNum = itemNum;
        this.itemName = itemName;
        this.itemQty = itemQty;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.itementry,parent,false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.itemNum.setText(String.valueOf(itemNum.get(position)));
        holder.itemName.setText(String.valueOf(itemName.get(position)));
        holder.itemQty.setText(String.valueOf(itemQty.get(position)));
    }

    @Override
    public int getItemCount() {
        return itemNum.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{
    TextView itemNum, itemName, itemQty;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNum = itemView.findViewById(R.id.textViewItemNum);
            itemName = itemView.findViewById(R.id.textViewItemName);
            itemQty = itemView.findViewById(R.id.textViewItemQty);
        }
    }
}
