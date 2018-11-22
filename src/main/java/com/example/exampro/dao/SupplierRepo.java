package com.example.exampro.dao;

import com.example.exampro.models.Supplier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class SupplierRepo implements ICRUDRepo<Supplier> {
    @Autowired
    private JdbcTemplate jdbc;
    private ArrayList<Supplier> suppliers = new ArrayList<>();

    public void create(Supplier supplier) {
        jdbc.update("INSERT INTO eksotisk_catering_db.supplier (supplier) VALUES('" + supplier.getSupplier() + "')");
    }

    public Supplier read(int id) {
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM eksotisk_catering_db.supplier WHERE id ='" + id + "'");
        if(rs.next()){
            return new Supplier(rs.getInt("id"), rs.getString("supplier"));
        }
        return  null;
    }

    public Supplier readString(String supplier) {
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM eksotisk_catering_db.supplier WHERE supplier ='" + supplier + "'");
        if(rs.next()){
            return new Supplier(rs.getInt("id"), rs.getString("supplier"));
        }
        return  null;
    }

    public ArrayList<Supplier> readAll() {
        SqlRowSet rs = jdbc.queryForRowSet("SELECT * FROM eksotisk_catering_db.supplier");
        suppliers.clear();
        while (rs.next()) {
            suppliers.add(new Supplier(rs.getInt("id"), rs.getString("supplier")));
        }
        return suppliers;
    }

    public void update(Supplier supplier) {
        jdbc.update("UPDATE eksotisk_catering_db.supplier SET supplier = '" + supplier.getSupplier() + "' WHERE id = '" + supplier.getId() + "'");
    }

    public void delete(Supplier supplier) {
        jdbc.update("DELETE FROM eksotisk_catering_db.supplier WHERE id = '" + supplier.getId() + "'");
    }
}
