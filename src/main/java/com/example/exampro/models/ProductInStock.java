package com.example.exampro.models;

import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public class ProductInStock {
    private int id;
    private Product product;
    private Integer quantity;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate receiptDate;
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate expirationDate;
    private User user;

    public ProductInStock() {

    }

    public ProductInStock(int id, Product product, Integer quantity, LocalDate receiptDate, LocalDate expirationDate, User user) {
        this.id = id;
        this.product = product;
        this.quantity = quantity;
        this.receiptDate = receiptDate;
        this.expirationDate = expirationDate;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public LocalDate getReceiptDate() {
        return receiptDate;
    }

    public void setReceiptDate(LocalDate receiptDate) {
        this.receiptDate = receiptDate;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate expirationDate) {
        this.expirationDate = expirationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String toString() {
        return "ProductInStock{" +
                "id=" + id +
                "product=" + product +
                ", quantity=" + quantity +
                ", receiptDate=" + receiptDate +
                ", expirationDate=" + expirationDate +
                ", user=" + user +
                '}';
    }
}
