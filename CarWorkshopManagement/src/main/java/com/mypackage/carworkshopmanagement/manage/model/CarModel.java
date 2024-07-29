package com.mypackage.carworkshopmanagement.manage.model;

public class CarModel {
    int id;
    String brand;
    Client owner;
    String frameNumber;
    double milage;

    public CarModel(int id,String frameNumber, String brand,double milage, Client owner) {
        this.id = id;
        this.brand = brand;
        this.owner = owner;
        this.milage = milage;
        this.frameNumber = frameNumber;
    }

    public CarModel() {

    }

    public CarModel(String frameNumber, String brand, double milage, Client owner) {
        this.brand = brand;
        this.owner = owner;
        this.milage = milage;
        this.frameNumber = frameNumber;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public double getMilage() {
        return milage;
    }

    public void setMilage(double milage) {
        this.milage = milage;
    }

    public Client getOwner() {
        return owner;
    }

    public void setOwner(Client owner) {
        this.owner = owner;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }
}
