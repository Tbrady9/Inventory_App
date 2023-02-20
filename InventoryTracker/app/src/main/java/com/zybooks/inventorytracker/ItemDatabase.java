package com.zybooks.inventorytracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.EditText;
import android.widget.TextView;

public class ItemDatabase extends SQLiteOpenHelper {
    private static ItemDatabase instance;
    private static final String DATABASE_NAME = "itemdata.db";
    private static final int VERSION = 1;

    ItemDatabase(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static ItemDatabase getInstance(Context context){
        if (instance == null){
            instance = new ItemDatabase(context);
        }
        return instance;
    }

    private static final class ItemTable{
        private static final String TABLE = "items";
        private static final String ITEM_NUM = "itemNum";
        private static final String ITEM_NAME = "itemName";
        private static final String ITEM_QTY = "qty";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE items (" +
                "itemNum INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL, " +
                "itemName text, " +
                "qty text" +
                ")");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + ItemTable.TABLE);
        onCreate(db);
    }

    public boolean addItem(String itemName, String itemQty) {

        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ItemTable.ITEM_NAME, itemName);
        cv.put(ItemTable.ITEM_QTY, itemQty);

        long result = db.insert(ItemTable.TABLE, null, cv);

        if (result == -1){
            return false;
        }
        else {
            return true;
        }

    }

    public Cursor getItemData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + ItemTable.TABLE, null);
        return cursor;
    }
}