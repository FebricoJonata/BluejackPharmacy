package com.example.mcs_lab.model;

public class Medicine {

    private Integer medicineId;
    private String name;
    private String manufacturer;
    private Integer price;
    private String image;
    private String description;

    public Medicine(Integer medicineId, String name, String manufacturer, Integer price, String image, String description) {
        this.medicineId = medicineId;
        this.name = name;
        this.manufacturer = manufacturer;
        this.price = price;
        this.image = image;
        this.description = description;
    }

    public Integer getMedicineId() {
        return medicineId;
    }

    public void setMedicineId(Integer medicineId) {
        this.medicineId = medicineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
