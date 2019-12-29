package com.gdpi.dao;

import com.gdpi.bean.User;

import java.util.List;
import java.util.Map;

public interface UserDao {
    User findByUserName(String username);

    List<User> getAll(Map<String,Object> queryMap);

    List<User> selectAll();

    int add(User user);

    int getTotal(Map<String,Object> queryMap);
    int updateUser(User user);
    int deleteUser(String ids);
}
