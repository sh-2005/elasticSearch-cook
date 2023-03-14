package com.xx.dao;

import com.xx.entity.Cook;

import java.util.List;

public interface CookDao {
    List<Cook> findAll();
    void add(Cook cook);
    void update(Cook cook);
    Cook selectOne(String id);
    void delete(String id);
}
