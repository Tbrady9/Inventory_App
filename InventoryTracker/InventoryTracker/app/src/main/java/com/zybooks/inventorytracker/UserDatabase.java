package com.zybooks.inventorytracker;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class UserDatabase extends SQLiteOpenHelper {

    private static UserDatabase instance;
    private static final String DATABASE_NAME = "data.db";
    private static final int VERSION = 2;

    private UserDatabase(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    public static UserDatabase getInstance(Context context){
        if (instance == null){
            instance = new UserDatabase(context);
        }
        return instance;
    }

    private static final class UserTable{
        private static final String TABLE = "users";
        private static final String USER_NAME = "userName";
        private static final String USER_FNAME = "userFName";
        private static final String USER_LNAME = "userLName";
        private static final String USER_PASSWORD = "userPassword";
        private static final String USER_ROLE = "userRole";
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + UserTable.TABLE + " (" +
        UserTable.USER_NAME + " text, " +
        UserTable.USER_FNAME + " text, " +
        UserTable.USER_LNAME + " text, " +
        UserTable.USER_PASSWORD + " text, " +
        UserTable.USER_ROLE + " text)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public boolean authenticate(String userName, String userPassword){
        boolean isAuthenticated = false;

        SQLiteDatabase db = getReadableDatabase();

        String sql = " select * from " + UserTable.TABLE +
        " where " + UserTable.USER_NAME + " = ? AND " +
        UserTable.USER_PASSWORD + " = ? ";

        Cursor cursor = db.rawQuery(sql, new String[]{userName, userPassword});

        if (cursor.moveToFirst()) {
            isAuthenticated = true;
        }
        return isAuthenticated;
    }
}
