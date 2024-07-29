package com.mypackage.carworkshopmanagement.manage.model;

public class Table1 {
     String brandOrTypeOfCar;
     String registrationNumber;
     String owner;
     String frameNumber;
     String milage;
     String customerPhone;
     String executor;
     String date;

    public Table1(String brandOrTypeOfCar, String registrationNumber, String owner, String milage, String frameNumber, String customerPhone, String executor, String date) {
        this.brandOrTypeOfCar = brandOrTypeOfCar;
        this.registrationNumber = registrationNumber;
        this.owner = owner;
        this.milage = milage;
        this.frameNumber = frameNumber;
        this.customerPhone = customerPhone;
        this.executor = executor;
        this.date = date;
    }

    public String getBrandOrTypeOfCar() {
        return brandOrTypeOfCar;
    }

    public void setBrandOrTypeOfCar(String brandOrTypeOfCar) {
        this.brandOrTypeOfCar = brandOrTypeOfCar;
    }

    public String getRegistrationNumber() {
        return registrationNumber;
    }

    public void setRegistrationNumber(String registrationNumber) {
        this.registrationNumber = registrationNumber;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public String getMilage() {
        return milage;
    }

    public void setMilage(String milage) {
        this.milage = milage;
    }

    public String getCustomerPhone() {
        return customerPhone;
    }

    public void setCustomerPhone(String customerPhone) {
        this.customerPhone = customerPhone;
    }

    public String getExecutor() {
        return executor;
    }

    public void setExecutor(String executor) {
        this.executor = executor;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
