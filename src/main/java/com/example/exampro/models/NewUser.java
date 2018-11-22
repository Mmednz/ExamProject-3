package com.example.exampro.models;


public class NewUser {
    private User user;
    private Password password;

    public NewUser(){

    }

    public NewUser(User user, Password password) {
        this.user = user;
        this.password = password;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Password getPassword() {
        return password;
    }

    public void setPassword(Password password) {
        this.password = password;
    }
}
