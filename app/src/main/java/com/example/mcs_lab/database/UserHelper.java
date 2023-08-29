package com.example.mcs_lab.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mcs_lab.model.User;

public class UserHelper {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public UserHelper(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void addUser(String name, String email, String password, String phone, String isVerified){
        db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put("name", name);
        values.put("email", email);
        values.put("password", password);
        values.put("phone", phone);
        values.put("isVerified", isVerified);
        db.insert("users", null, values);
        db.close();
    }
    public User authUser(String email, String password){
        db = dbHelper.getReadableDatabase();

        String[] projection = {
                "userid", "name", "email", "password", "phone", "isverified"
        };

        String selection = "email = ? and password = ?";
        String[] selectionArgs = {email, password};

        Cursor cursor = db.query(
                "users",
                projection,
                selection,
                selectionArgs,
                null, null, null
        );
        User user = null;
        if(cursor.moveToFirst()){
            cursor.moveToFirst();
            System.out.println(cursor.getInt(0));
            System.out.println(cursor.getString(1));
            user = new User(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getString(3), cursor.getString(4), cursor.getString(5));
        }
        db.close();
        return user;
    }

    public void verifiedUser(int id){
        db = dbHelper.getWritableDatabase();

        String query = "UPDATE users set isVerified = 'true' where userid = " + id;
        db.execSQL(query);
    }

}
