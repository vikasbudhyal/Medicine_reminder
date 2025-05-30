package com.example.medicine_reminder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;
import android.content.ContentValues;

public class DBhelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "MedicineApp.db";
    public static final String TABLE_NAME = "users";

    public static final String COL_1 = "ID";
    public static final String COL_2 = "fullName";
    public static final String COL_3 = "dob";
    public static final String COL_4 = "email";
    public static final String COL_5 = "password";

    public DBhelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (" +
                COL_1 + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COL_2 + " TEXT, " +
                COL_3 + " TEXT, " +
                COL_4 + " TEXT UNIQUE, " +  // email should be unique
                COL_5 + " TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // Insert user data during registration
    public boolean insertUser(String fullName, String dob, String email, String password) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_2, fullName);
        values.put(COL_3, dob);
        values.put(COL_4, email);
        values.put(COL_5, password);

        long result = db.insert(TABLE_NAME, null, values);
        return result != -1;
    }

    // Check if user exists by email
    public boolean checkUser(String email) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_4 + " = ?", new String[]{email});
        boolean exists = cursor.getCount() > 0;
        cursor.close();
        return exists;
    }

    // Check login credentials
    public boolean checkUser(String email, String password) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_NAME + " WHERE " + COL_4 + " = ? AND " + COL_5 + " = ?", new String[]{email, password});
        boolean isValid = cursor.getCount() > 0;
        cursor.close();
        return isValid;
    }
}
