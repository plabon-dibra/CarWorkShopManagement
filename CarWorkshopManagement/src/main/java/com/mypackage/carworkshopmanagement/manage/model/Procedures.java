package com.mypackage.carworkshopmanagement.manage.model;

public class Procedures {
    private int id;
    private String procedure;
    private String observation;
    private double priceWithTax;

    public Procedures(int id, String procedure, String observation, double priceWithTax) {
        this.id = id;
        this.procedure = procedure;
        this.observation = observation;
        this.priceWithTax = priceWithTax;
    }

    public Procedures(String procedure, String observation, double priceWithTax) {
        this.procedure = procedure;
        this.observation = observation;
        this.priceWithTax = priceWithTax;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getProcedure() {
        return procedure;
    }

    public void setProcedure(String procedure) {
        this.procedure = procedure;
    }

    public String getObservation() {
        return observation;
    }

    public void setObservation(String observation) {
        this.observation = observation;
    }

    public double getPriceWithTax() {
        return priceWithTax;
    }

    public void setPriceWithTax(double priceWithTax) {
        this.priceWithTax = priceWithTax;
    }
}
