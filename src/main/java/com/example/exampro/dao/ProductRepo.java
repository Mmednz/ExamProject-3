package com.example.exampro.dao;

import com.example.exampro.models.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;


@Repository
public class ProductRepo implements ICRUDRepo<Product> {

    @Autowired
    private JdbcTemplate jdbc;
    private ArrayList<Product> products = new ArrayList<Product>();
    @Autowired
    private SupplierRepo supplierRepo = new SupplierRepo();

    @Override
    public void create(Product product) {
        jdbc.update("INSERT INTO eksotisk_catering_db.products (name, type, fk_supplier_id, barcode) VALUES ('"+ product.getName() +"','"+ product.getType() +"','"+ product.getSupplier().getId() +"','"+ product.getBarcode() +"')");
        System.out.println("Product form has been created and added to product_forms db");
    }

    @Override
    public ArrayList<Product> readAll() {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM eksotisk_catering_db.products");
        products.clear();

        while(sqlRowSet.next()){
            products.add(new Product(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getString("type"), sqlRowSet.getString("barcode"), supplierRepo.read(sqlRowSet.getInt("fk_supplier_id"))));
        }
        return products;
    }

    public Product read(int id) {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM eksotisk_catering_db.products WHERE id = '"+ id +"'");

        if (sqlRowSet.next()){
            return new Product(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getString("type"), sqlRowSet.getString("barcode"), supplierRepo.read(sqlRowSet.getInt("fk_supplier_id")));
        }
        return null;
    }

    public Product readBarcode(String barcode) {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM eksotisk_catering_db.products WHERE barcode = '"+ barcode +"'");

        if (sqlRowSet.next()){
            return new Product(sqlRowSet.getInt("id"), sqlRowSet.getString("name"), sqlRowSet.getString("type"), sqlRowSet.getString("barcode"), supplierRepo.read(sqlRowSet.getInt("fk_supplier_id")));
        }
        return null;
    }

    @Override
    public void update(Product product) {
        jdbc.update("UPDATE eksotisk_catering_db.products SET name ='"+ product.getName() + "', type ='"+ product.getType() + "', fk_supplier_id = '" + product.getSupplier().getId() + "' WHERE barcode = '"+ product.getBarcode() +"'");
    }

    @Override
    public void delete(Product product) {
        jdbc.update("DELETE FROM eksotisk_catering_db.products WHERE id ='"+ product.getId() +"'");
    }
}
