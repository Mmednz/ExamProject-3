package com.example.exampro.models;


public class User {
    private int id;
    private String firstname;
    private String lastname;
    private int password = 0;
    private String role;

    public User(){

    }

    public User(int id, String firstname, String lastname, int password, String role) {
            this.firstname = firstname;
            this.lastname = lastname;
            this.password = password;
            this.id = id;
            this.role = role;
    }

    public String getFirstname() {
            return firstname;
    }

    public void setFirstname(String firstname) {
            this.firstname = firstname;
    }

    public String getLastname() {
            return lastname;
    }

    public void setLastname(String lastname) {
            this.lastname = lastname;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
                this.password = password;
        }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return firstname + " " + lastname;
    }
}