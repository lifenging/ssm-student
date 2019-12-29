package com.gdpi.service.impl;

import com.gdpi.bean.User;
import com.gdpi.dao.UserDao;
import com.gdpi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserDao userDao;

    @Override
    public User findByUserName(String username) {
        return userDao.findByUserName(username);
    }

    @Override
    public List<User> getAll(Map<String, Object> queryMap) {
        return userDao.getAll(queryMap);
    }

    @Override
    public List<User> selectAll() {
        return userDao.selectAll();
    }

    @Override
    public int add(User user) {
        return userDao.add(user);
    }

    @Override
    public int getTotal(Map<String, Object> queryMap) {
        return userDao.getTotal(queryMap);
    }

    @Override
    public int updateUser(User user) {
        return userDao.updateUser(user);
    }

    @Override
    public int deleteUser(String ids) {
        return userDao.deleteUser(ids);
    }


}
