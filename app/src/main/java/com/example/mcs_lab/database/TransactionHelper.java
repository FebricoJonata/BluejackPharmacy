package com.example.mcs_lab.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.mcs_lab.helper.SessionManager;
import com.example.mcs_lab.model.Medicine;
import com.example.mcs_lab.model.Transaction;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class TransactionHelper {

    private DatabaseHelper dbHelper;
    private SQLiteDatabase db;

    public TransactionHelper(Context context){
        dbHelper = new DatabaseHelper(context);
    }

    public void addTransaction(Integer medicineId, Integer userId, String transactionDate, Integer quantity){
        db = dbHelper.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("medicineId", medicineId);
        values.put("userId", userId);
        values.put("date", transactionDate);
        values.put("quantity", quantity);
        db.insert("transactions", null, values);
        db.close();
    }

    public ArrayList<Transaction> getAllTransaction(int userid){
        db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("select t.medicineid, date, quantity, m.name, m.manufacturer, m.price, m.image, m.description, t.userid, t.transactionid " +
                "from transactions t join medicines m on t.medicineid = m.medicineid " +
                "where userid = ? ", new String[]{String.valueOf(userid)});
        ArrayList<Transaction> transactions = new ArrayList<>();
        while (cursor.moveToNext()){
            Medicine medicine = new Medicine(Integer.parseInt(cursor.getString(0)), cursor.getString(3), cursor.getString(4), Integer.parseInt(cursor.getString(5)), cursor.getString(6), cursor.getString(7));
            transactions.add(new Transaction(Integer.parseInt(cursor.getString(9)), Integer.parseInt(cursor.getString(0)), Integer.parseInt(cursor.getString(8)), cursor.getString(1), Integer.parseInt(cursor.getString(2)), medicine));
        }
        cursor.close();
        db.close();
        return transactions;
    }

    public void updateTransaction(int userid, int medicineid, int quantity){
        db = dbHelper.getWritableDatabase();
        String query = "update transactions set quantity = ? where userid = ? and medicineid = ?";
        db.execSQL(query, new String[]{String.valueOf(quantity), String.valueOf(userid), String.valueOf(medicineid)});
        db.close();
    }

    public void deleteTransaction(int userid, int medicineid){
        db = dbHelper.getWritableDatabase();
        String query = "delete from transactions where userid = ? and medicineid = ?";
        db.execSQL(query, new String[]{String.valueOf(userid), String.valueOf(medicineid)});
        db.close();
    }

}
