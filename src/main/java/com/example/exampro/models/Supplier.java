package com.example.exampro.models;

public class Supplier {
    private int id;
    private String supplier;

    public Supplier(){

    }

    public Supplier(int id, String supplier) {
        this.id = id;
        this.supplier = supplier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSupplier() {
        return supplier;
    }

    public void setSupplier(String supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Supplier{" +
                "id=" + id +
                ", supplier='" + supplier + '\'' +
                '}';
    }
}
