package com.xx.service;

import com.xx.entity.Cook;

import java.io.IOException;
import java.util.List;

public interface CookService {
    List<Cook> findAll();
    void add(Cook cook);
    void update(Cook cook);
    Cook selectOne(String id);
    void delete(String id);
    void createIndexAll() throws IOException;
    void clearIndexAll() throws IOException;
}
