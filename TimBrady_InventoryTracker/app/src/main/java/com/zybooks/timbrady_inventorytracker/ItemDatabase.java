package com.zybooks.timbrady_inventorytracker;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

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

    // Add an item to the database
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

    // Update the quantity of an item in the database
    public boolean updateItem(String itemNum, String itemName, String itemQty){
        boolean itemUpdated = false;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        cv.put(ItemTable.ITEM_NAME, itemName);
        cv.put(ItemTable.ITEM_QTY, itemQty);

        String where = "itemNum = ?";
        String[] whereArgs = new String[]{itemNum};

        long result = db.update(ItemTable.TABLE, cv, where, whereArgs);
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    // Delete an item from the database
    public boolean deleteItem(String itemNum){
        boolean itemDeleted = false;
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();

        String where = "itemNum = ?";
        String[] whereArgs = new String[]{itemNum};

        long result = db.delete(ItemTable.TABLE, where, whereArgs);
        if (result == -1){
            return false;
        }
        else{
            return true;
        }
    }

    public Cursor getItemData(){
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("Select * from " + ItemTable.TABLE, null);
        return cursor;
    }
}

