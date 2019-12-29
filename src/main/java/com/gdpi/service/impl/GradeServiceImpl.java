package com.gdpi.service.impl;

import com.gdpi.bean.Grade;
import com.gdpi.dao.GradeDao;
import com.gdpi.service.GradeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class GradeServiceImpl implements GradeService {

    @Autowired
    GradeDao gradeDao;

    @Override
    public List<Grade> findList(Map<String, Object> queryMap) {
        return gradeDao.findList(queryMap);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return gradeDao.getTotal(queryMap);
    }

    @Override
    public void add(Grade grade) {
         gradeDao.add(grade);
    }

    @Override
    public void update(Grade grade) {
        gradeDao.update(grade);
    }

    @Override
    public Grade findByName(String name) {
        return gradeDao.findByName(name);
    }

    @Override
    public int delete(String ids) {

        return gradeDao.delete(ids);
    }

    @Override
    public List<Grade> findAll() {
        return gradeDao.findAll();
    }
}
