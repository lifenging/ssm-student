package com.gdpi.service;

import com.gdpi.bean.Clazz;

import java.util.List;
import java.util.Map;

public interface ClazzService {
    Clazz findByName(String name);
    void add(Clazz clazz);
    void update(Clazz clazz);
    int delete(String ids);
    public List<Clazz> findList(Map<String, Object> queryMap);
    public List<Clazz> findAll();
    public int getTotal(Map<String, Object> queryMap);
}
