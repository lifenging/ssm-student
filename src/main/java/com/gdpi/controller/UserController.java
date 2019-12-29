package com.gdpi.controller;

import com.gdpi.bean.Page;
import com.gdpi.bean.User;
import com.gdpi.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {

    @Autowired
    UserService userService;

    /**
     * 用户列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView model) {
        model.setViewName("user/user_list");
        return model;
    }

    /**
     * 列表查询，分页
     *
     * @param username
     * @return
     */
    @RequestMapping(value = "/get_list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(@RequestParam(value = "username", required = false, defaultValue = "") String username,
                                       Page page) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("username", "%" + username + "%");
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());

      /*  PageHelper.startPage(page, rows, true);
        List<User> list = userService.selectAll();
        PageInfo<User> info = new PageInfo<>(list);
        list = info.getList();
        long count = info.getTotal();*/

//        map.put("rows",list);
        map.put("rows", userService.getAll(queryMap));
        map.put("total", userService.getTotal(queryMap));

        return map;
    }


    /**
     * 添加
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> add(User user) {
        Map<String, Object> map = new HashMap<>();
        if (user == null) {
            map.put("type", "error");
            map.put("msg", "数据绑定出错，请联系开发作者!");
            return map;
        }
        if (StringUtils.isEmpty(user.getUsername())) {
            map.put("type", "error");
            map.put("msg", "用户名不能为空!");
            return map;
        }

        User existUser = userService.findByUserName(user.getUsername());
        if (existUser != null) {
            map.put("type", "error");
            map.put("msg", "该用户名已经存在!");
            return map;
        }

        if (userService.add(user) > 1) {
            map.put("type", "error");
            map.put("msg", "添加失败!");
            return map;
        }


        map.put("type", "success");
        map.put("msg", "添加成功!");
        return map;
    }

    /**
     * 修改
     *
     * @param user
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(User user) {
        Map<String, Object> map = new HashMap<>();
        if (user == null) {
            map.put("type", "error");
            map.put("msg", "数据绑定出错，请联系开发作者!");
            return map;
        }
        if (StringUtils.isEmpty(user.getUsername())) {
            map.put("type", "error");
            map.put("msg", "用户名不能为空!");
            return map;
        }

        User existUser = userService.findByUserName(user.getUsername());
        if (existUser != null) {
            map.put("type", "error");
            map.put("msg", "该用户名已经存在!");
            return map;
        }

        // System.out.println("修改"+userService.updateUser(user));
        if (userService.updateUser(user) != -2147482646) {
            map.put("type", "error");
            map.put("msg", "修改失败!");
            return map;
        }

        map.put("type", "success");
        map.put("msg", "修改成功!");
        return map;

    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("ids[]") Long[] ids) {
        Map<String, Object> map = new HashMap<>();
        if (ids == null) {
            map.put("type", "error");
            map.put("msg", "请选择要删除的数据!");
            return map;
        }

        String idsStr = "";
        for (Long id : ids) {
            idsStr += id + ",";

        }
        idsStr = idsStr.substring(0, idsStr.length() - 1);

//        userService.deleteUser(idsStr);
        if (userService.deleteUser(idsStr) != -2147482646) {
            map.put("type", "error");
            map.put("msg", "删除失败!");
            return map;
        }

        map.put("type", "success");
        map.put("msg", "删除成功");

        return map;
    }


}
