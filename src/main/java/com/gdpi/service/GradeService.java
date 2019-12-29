package com.gdpi.service;

import com.gdpi.bean.Grade;

import java.util.List;
import java.util.Map;

public interface GradeService {
    List<Grade> findList(Map<String,Object> queryMap);
    int getTotal(Map<String,Object> queryMap);
    void add(Grade grade);
    void update(Grade grade);
    Grade findByName(String name);

    int delete(String ids);
    List<Grade> findAll();
}
