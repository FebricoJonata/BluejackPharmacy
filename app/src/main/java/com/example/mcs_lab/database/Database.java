package com.example.mcs_lab.database;

import android.content.Context;

import com.example.mcs_lab.model.Medicine;
import com.example.mcs_lab.model.Transaction;
import com.example.mcs_lab.model.User;

import java.util.ArrayList;

public class Database {

    private static Database instance;
    private UserHelper userHelper;
    private MedicineHelper medicineHelper;
    private TransactionHelper transactionHelper;

    private Database(Context context){
        userHelper = new UserHelper(context);
        medicineHelper = new MedicineHelper(context);
        transactionHelper = new TransactionHelper(context);
    }

    public static Database getInstance(Context context){
        if(instance==null){
            synchronized (Database.class){
                if(instance==null){
                    instance = new Database(context);
                }
            }
        }
        return instance;
    }

    public void addUser(String name, String email, String password, String phone, String isVerified){
        userHelper.addUser(name, email, password, phone, isVerified);
    }

    public User authUser(String email, String password){
        return userHelper.authUser(email, password);
    }

    public void verifiedUser(int id){
        userHelper.verifiedUser(id);
    }

    public void addMedicine(String name, String manufacturer, Integer price, String image, String description){
        medicineHelper.addMedicine(name, manufacturer, price, image, description);
    }

    public void addTransaction(Integer medicineId, Integer userId, String transactionDate, Integer quantity){
        transactionHelper.addTransaction(medicineId, userId, transactionDate, quantity);
    }

    public ArrayList<Transaction> getAllTransaction(int userid){
        return transactionHelper.getAllTransaction(userid);
    }

    public void updateTransaction(int userid, int medicineid, int quantity){
        transactionHelper.updateTransaction(userid, medicineid, quantity);
    }

    public void deleteTransaction(int userid, int medicineid){
        transactionHelper.deleteTransaction(userid, medicineid);
    }

    public ArrayList<Medicine> getMedicine(){
        return medicineHelper.getMedicine();
    }
    public void deleteAllMedicine(){
        medicineHelper.deleteAllMedicine();
    }
}
