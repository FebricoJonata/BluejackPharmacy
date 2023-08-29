package com.example.mcs_lab.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mcs_lab.model.Medicine;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MedicineHelper {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public MedicineHelper(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void addMedicine(String name, String manufacturer, Integer price, String image, String description){
        db = dbHelper.getWritableDatabase();
        if(!medicineExists(name, manufacturer)){
            ContentValues values = new ContentValues();
            values.put("name", name);
            values.put("manufacturer", manufacturer);
            values.put("price", price);
            values.put("image", image);
            values.put("description", description);
            db.insert("medicines", null, values);
            db.close();
            System.out.println("Already has item");
        }
    }

    private boolean medicineExists(String name, String manufacturer){
        db = dbHelper.getReadableDatabase();
        String[] projection = {
                "medicineid",
                "name",
                "manufacturer",
                "price",
                "image",
                "description"
        };
        String selection = "name = ? and manufacturer = ?";
        String[] selectionargs = {name, manufacturer};
        Cursor cursor = db.query(
                "medicines",
                projection,
                selection, selectionargs, null, null, null
        );
        if(cursor.moveToFirst()){
            return true;
        }
        return false;
    }

    public ArrayList<Medicine> getMedicine(){
        db = dbHelper.getReadableDatabase();
        ArrayList<Medicine> medicines = new ArrayList<>();
        String[] projection = {
                "medicineid",
                "name",
                "manufacturer",
                "price",
                "image",
                "description"
        };

//        Cursor cursor = db.query(
//                "medicines",
//                projection,
//                null, null, null, null, null
//        );
        Cursor cursor = db.rawQuery("SELECT * FROM medicines", null);
        while(cursor.moveToNext()){
            Medicine medicine = new Medicine(cursor.getInt(0), cursor.getString(1), cursor.getString(2), cursor.getInt(3), cursor.getString(4) , cursor.getString(5));
            System.out.println(cursor.getColumnIndex("name"));
            medicines.add(medicine);
        }
        cursor.close();
        db.close();
        return medicines;
    }

    public void deleteAllMedicine(){
        db = dbHelper.getWritableDatabase();
        db.delete("medicines", null, null);
        db.close();
    }
}
