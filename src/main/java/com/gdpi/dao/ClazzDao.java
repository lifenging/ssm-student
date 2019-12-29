package com.gdpi.dao;

import com.gdpi.bean.Clazz;

import java.util.List;
import java.util.Map;

public interface ClazzDao {
    Clazz findByName(String name);
    public void add(Clazz clazz);
    public void update(Clazz clazz);
    public int delete(String ids);
    public List<Clazz> findList(Map<String, Object> queryMap);
    public List<Clazz> findAll();
    public int getTotal(Map<String, Object> queryMap);
}
