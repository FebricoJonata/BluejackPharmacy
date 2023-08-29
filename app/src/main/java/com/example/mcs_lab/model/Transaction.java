package com.example.mcs_lab.model;

public class  Transaction {

    private Integer transactionId;
    private Integer medicineId;
    private Integer userId;
    private String date;
    private Integer quantity;
    private Medicine medicine;

    public Transaction(Integer transactionId, Integer medicineId, Integer userId, String date, Integer quantity, Medicine medicine) {
        this.transactionId = transactionId;
        this.medicineId = medicineId;
        this.userId = userId;
        this.date = date;
        this.quantity = quantity;
        this.medicine = medicine;
    }

    public Medicine getMedicine(){return medicine;}

    public void setMedicine(Medicine medicine){this.medicine = medicine;}

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

}
