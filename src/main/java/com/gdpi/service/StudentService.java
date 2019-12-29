package com.gdpi.service;

import com.gdpi.bean.Student;

import java.util.List;
import java.util.Map;

public interface StudentService {
    Student findByName(String username);
    void add(Student student);
    void update(Student student);
    int delete(String ids);
    public List<Student> findList(Map<String, Object> queryMap);
    public List<Student> findAll();
    public int getTotal(Map<String, Object> queryMap);
}
