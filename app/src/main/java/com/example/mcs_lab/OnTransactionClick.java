package com.example.mcs_lab;

public interface OnTransactionClick {

    void onUpdate(int userid, int medicineid, int quantity);
    void onDelete(int userid, int medicineid);
}
