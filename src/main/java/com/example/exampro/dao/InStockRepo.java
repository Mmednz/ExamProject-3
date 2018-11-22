package com.example.exampro.dao;

import com.example.exampro.models.ProductInStock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.ArrayList;


@Repository
public class InStockRepo implements ICRUDRepo<ProductInStock> {

    @Autowired
    private JdbcTemplate jdbc;
    private ArrayList<ProductInStock> products = new ArrayList<>();
    @Autowired
    private ProductRepo productRepo = new ProductRepo();
    @Autowired
    private UserRepo userRepo = new UserRepo();


    @Override
    public void create(ProductInStock productInStock) {
        jdbc.update("INSERT INTO eksotisk_catering_db.products_in_stock(fk_product_id, quantity, receipt_date, expiration_date, fk_user_id) VALUES ('" + productInStock.getProduct().getId() + "','" + productInStock.getQuantity() + "','" +
                productInStock.getReceiptDate() + "','" + productInStock.getExpirationDate() + "','" + productInStock.getUser().getId() + "')");
    }

    public ProductInStock read(int id) {
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM eksotisk_catering_db.products_in_stock WHERE id = '" + id + "'");
        if (rs.next()) {
            return new ProductInStock(rs.getInt("id"),
                    productRepo.read(rs.getInt("fk_product_id")),
                    rs.getInt("quantity"),
                    rs.getDate("receipt_date").toLocalDate(),
                    rs.getDate("expiration_date").toLocalDate(),
                    userRepo.read(rs.getInt("fk_user_id")));
        }

        return null;
    }


    @Override
    public ArrayList<ProductInStock> readAll() {
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM eksotisk_catering_db.products_in_stock");
        products.clear();
        while (rs.next()) {
            products.add(new ProductInStock(rs.getInt("id"), productRepo.read(rs.getInt("fk_product_id")), rs.getInt("quantity"), rs.getDate("receipt_date").toLocalDate(),
                    rs.getDate("expiration_date").toLocalDate(), userRepo.read(rs.getInt("fk_user_id"))));
        }
        return products;
    }

    public ProductInStock checkExpirationDate(ProductInStock productInStock) {
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM eksotisk_catering_db.products_in_stock");
        products.clear();
        while (rs.next()) {
            if (productInStock.getExpirationDate().isAfter(rs.getDate("expiration_date").toLocalDate()) && productRepo.read(rs.getInt("fk_product_id")).getBarcode().equals(productInStock.getProduct().getBarcode()) || productInStock.getExpirationDate().isEqual(rs.getDate("expiration_date").toLocalDate()) && productRepo.read(rs.getInt("fk_product_id")).getBarcode().equals(productInStock.getProduct().getBarcode())) {
                products.add(new ProductInStock(rs.getInt("id"), productRepo.read(rs.getInt("fk_product_id")), rs.getInt("quantity"), rs.getDate("receipt_date").toLocalDate(), rs.getDate("expiration_date").toLocalDate(), userRepo.read(rs.getInt("fk_user_id"))));
            }
        }
        if (products == null) {
            return null;
        }
        else {
            LocalDate ld = null;
            for (ProductInStock pr : products) {
                if(ld == null){
                    ld = pr.getExpirationDate();
                }
                if (pr.getExpirationDate().isBefore(ld)) {
                    ld = pr.getExpirationDate();
                }
            }
            for (ProductInStock pr : products) {
                if (pr.getExpirationDate().isEqual(ld)) {
                    return pr;
                }
            }
        }
        return null;
    }

    @Override
    public void update(ProductInStock productInStock) {
        jdbc.update("UPDATE eksotisk_catering_db.products_in_stock SET quantity='"+ productInStock.getQuantity() + "', expiration_date = '" + productInStock.getExpirationDate() + "'WHERE id = '" + productInStock.getId() + "'");
    }

    @Override
    public void delete(ProductInStock productInStock) {
        System.out.println(productInStock.getId());
        jdbc.update("DELETE from eksotisk_catering_db.products_in_stock WHERE id = '" + productInStock.getId() + "'");
    }

}