package com.gdpi.service;

import com.gdpi.bean.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    User findByUserName(String username);
    List<User> getAll(Map<String,Object> queryMap);
    List<User> selectAll();
    int add(User user);
    int getTotal(Map<String,Object> queryMap);
    int updateUser(User user);
    int deleteUser(String ids);

}
