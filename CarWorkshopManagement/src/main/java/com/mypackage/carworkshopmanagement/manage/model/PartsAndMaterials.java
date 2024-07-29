package com.mypackage.carworkshopmanagement.manage.model;

public class PartsAndMaterials {
    private int id;
    private String partsAndMaterial;
    private String partCode;
    private double priceWithTax;
    private int quantity;

    public PartsAndMaterials(int id, String partsAndMaterial, String partCode, double priceWithTax) {
        this.id = id;
        this.partsAndMaterial = partsAndMaterial;
        this.partCode = partCode;
        this.priceWithTax = priceWithTax;
        quantity = 0;
    }

    public PartsAndMaterials( String partsAndMaterial, String partCode, double priceWithTax) {
        this.partsAndMaterial = partsAndMaterial;
        this.partCode = partCode;
        this.priceWithTax = priceWithTax;
        quantity = 0;
    }
    public PartsAndMaterials( String partsAndMaterial, String partCode, double priceWithTax, int quantity) {
        this.partsAndMaterial = partsAndMaterial;
        this.partCode = partCode;
        this.priceWithTax = priceWithTax;
        this.quantity = quantity;
    }


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPartsAndMaterial() {
        return partsAndMaterial;
    }

    public void setPartsAndMaterial(String partsAndMaterial) {
        this.partsAndMaterial = partsAndMaterial;
    }

    public String getPartCode() {
        return partCode;
    }

    public void setPartCode(String partCode) {
        this.partCode = partCode;
    }

    public double getPriceWithTax() {
        return priceWithTax;
    }

    public void setPriceWithTax(double priceWithTax) {
        this.priceWithTax = priceWithTax;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
