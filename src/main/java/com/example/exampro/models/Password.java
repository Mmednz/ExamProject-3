package com.example.exampro.models;


public class Password {
    private int password = 0;

    public Password(){

    }

    public Password(int password){
        this.password = password;
    }

    public int getPassword() {
        return password;
    }

    public void setPassword(int password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "Password{" +
                "password=" + password +
                '}';
    }
}


