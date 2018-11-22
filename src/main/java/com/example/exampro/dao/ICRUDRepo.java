package com.example.exampro.dao;

import java.util.ArrayList;

public interface ICRUDRepo<T> {

    void create(T object);

    T read(int id);

    ArrayList<T> readAll();

    void update(T object);

    void delete(T object);
}
