package com.mypackage.carworkshopmanagement.manage.model;

public class Client {
    int id;
    String regNo;
    String phoneNo;
    String name;

    public Client(int id, String name, String regNo, String phoneNo) {
        this.id = id;
        this.regNo = regNo;
        this.phoneNo = phoneNo;
        this.name = name;
    }

    public Client(String name, String regNo, String phoneNo) {
        this.regNo = regNo;
        this.phoneNo = phoneNo;
        this.name = name;
    }

    public String getRegNo() {
        return regNo;
    }

    public void setRegNo(String regNo) {
        this.regNo = regNo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

