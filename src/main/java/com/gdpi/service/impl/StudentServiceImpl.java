package com.gdpi.service.impl;

import com.gdpi.bean.Student;
import com.gdpi.dao.StudentDao;
import com.gdpi.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class StudentServiceImpl implements StudentService {

    @Autowired
    StudentDao studentDao;


    @Override
    public Student findByName(String username) {
        return studentDao.findByName(username);
    }

    @Override
    public void add(Student student) {
        studentDao.add(student);
    }

    @Override
    public void update(Student student) {
            studentDao.update(student);
    }

    @Override
    public int delete(String ids) {
        return studentDao.delete(ids);
    }

    @Override
    public List<Student> findList(Map<String, Object> queryMap) {
        return studentDao.findList(queryMap);
    }

    @Override
    public List<Student> findAll() {
        return studentDao.findAll();
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return studentDao.getTotal(queryMap);
    }
}
