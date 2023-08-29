package com.example.mcs_lab.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String database = "BluejackPharmacy";
    private static final int version = 1;
    private static final String userTable = "create table users (UserId integer primary key autoincrement, " +
            "Name text, " +
            "Email text," +
            "Password text," +
            "Phone text," +
            "isVerified text)";

    private static final String medicineTable = "create table medicines (MedicineId integer primary key autoincrement," +
            "Name text," +
            "Manufacturer text," +
            "Price integer," +
            "Image text," +
            "Description text)";

    private static final String transactionTable = "create table transactions (TransactionId integer primary key autoincrement," +
            "MedicineId integer," +
            "UserId integer," +
            "Date date," +
            "Quantity integer)";

    public DatabaseHelper(@Nullable Context context) {
        super(context, database, null, version);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(userTable);
        db.execSQL(medicineTable);
        db.execSQL(transactionTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop table if exists users ");
        db.execSQL("drop table if exists medicines ");
        db.execSQL("drop table if exists transactions");
    }

}
