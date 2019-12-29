package com.gdpi.service.impl;

import com.gdpi.bean.Clazz;
import com.gdpi.dao.ClazzDao;
import com.gdpi.service.ClazzService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ClazzServiceImpl implements ClazzService {

    @Autowired
    ClazzDao clazzDao;

    @Override
    public Clazz findByName(String name) {
        return clazzDao.findByName(name);
    }

    @Override
    public void add(Clazz clazz) {
        clazzDao.add(clazz);
    }

    @Override
    public void update(Clazz clazz) {
        clazzDao.update(clazz);
    }

    @Override
    public int delete(String ids) {
        return clazzDao.delete(ids);
    }

    @Override
    public List<Clazz> findList(Map<String, Object> queryMap) {
        return clazzDao.findList(queryMap);
    }

    @Override
    public List<Clazz> findAll() {
        return clazzDao.findAll();
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return clazzDao.getTotal(queryMap);
    }
}
