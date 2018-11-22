package com.example.exampro.models;


public class Product {

    private int id;
    private String name;
    private String type;
    private String barcode;
    private Supplier supplier;

    public Product() {

    }

    public Product(int id, String name, String type, String barcode, Supplier supplier) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.barcode = barcode;
        this.supplier = supplier;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getBarcode() {
        return barcode;
    }

    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    public Supplier getSupplier() {
        return supplier;
    }

    public void setSupplier(Supplier supplier) {
        this.supplier = supplier;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", type='" + type + '\'' +
                ", barcode=" + barcode +
                ", supplier=" + supplier +
                '}';
    }
}