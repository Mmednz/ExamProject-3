package com.example.exampro.dao;

import com.example.exampro.models.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;

@Repository
public class UserRepo implements ICRUDRepo<User> {

    @Autowired
    private JdbcTemplate jdbc;
    private ArrayList<User> users = new ArrayList<>();
    private User loggedInUser = new User();

    @Override
    public void create(User user) {
        jdbc.update("INSERT INTO eksotisk_catering_db.users (firstname, lastname, password, role) VALUES ('"+ user.getFirstname() +"','"+ user.getLastname() +"','"+ user.getPassword() +"','"+ user.getRole() +"')");
        System.out.println("Product form has been created and added to product_forms db");
    }

    @Override
    public ArrayList<User> readAll() {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM eksotisk_catering_db.users");
        users.clear();

        while(sqlRowSet.next()){
            users.add(new User(sqlRowSet.getInt("id"), sqlRowSet.getString("firstname"), sqlRowSet.getString("lastname"), sqlRowSet.getInt("password"), sqlRowSet.getString("role")));
        }

        return users;
    }

    public User login(int password){
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM eksotisk_catering_db.users WHERE password = '"+ password +"'");

        if(sqlRowSet.next()){
            loggedInUser = new User(sqlRowSet.getInt("id"),sqlRowSet.getString("firstname"),sqlRowSet.getString("lastname"),sqlRowSet.getInt("password"),sqlRowSet.getString("role"));
            return new User(sqlRowSet.getInt("id"),sqlRowSet.getString("firstname"),sqlRowSet.getString("lastname"),sqlRowSet.getInt("password"),sqlRowSet.getString("role"));
        }

        return null;
    }

    public User read(int id){
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM eksotisk_catering_db.users WHERE id = '"+ id +"'");

        if(sqlRowSet.next()){
            return new User(sqlRowSet.getInt("id"),sqlRowSet.getString("firstname"),sqlRowSet.getString("lastname"),sqlRowSet.getInt("password"),sqlRowSet.getString("role"));
        }
        return null;
    }

    @Override
    public void update(User user) {
        jdbc.update("UPDATE eksotisk_catering_db.users SET firstname = '"+ user.getFirstname() +"', lastname ='"+ user.getLastname() +"', password ='"+ user.getPassword() +"', role ='"+ user.getRole() +"'WHERE id ='"+ user.getId() +"'");
    }

    @Override
    public void delete(User user) {
        jdbc.update("DELETE FROM eksotisk_catering_db.users WHERE id = '"+ user.getId() +"'");
    }

    public User checkPassword(int password) {
        SqlRowSet sqlRowSet = jdbc.queryForRowSet("SELECT * FROM eksotisk_catering_db.users WHERE password = '"+ password + "'");

        if(sqlRowSet.next()){
            return new User(sqlRowSet.getInt("id"),sqlRowSet.getString("firstname"),sqlRowSet.getString("lastname"),sqlRowSet.getInt("password"),sqlRowSet.getString("role"));
        }
        return null;
    }

    public User getLoggedInUser(){
        return loggedInUser;
    }
}
