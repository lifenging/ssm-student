package com.gdpi.controller;

import com.gdpi.bean.Grade;
import com.gdpi.bean.Page;
import com.gdpi.bean.User;
import com.gdpi.service.GradeService;
import com.gdpi.service.UserService;
import com.github.pagehelper.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/grade")
public class GradeController {

    @Autowired
    GradeService gradeService;

    /**
     * 年级列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView model) {
        model.setViewName("grade/grade_list");
        return model;
    }

    /**
     * 列表查询，分页
     *
     * @param name
     * @return
     */
    @RequestMapping(value = "/get_list", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> getList(@RequestParam(value = "name", required = false, defaultValue = "") String name,
                                       Page page) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("name", "%" + name + "%");
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());

        map.put("rows", gradeService.findList(queryMap));
        map.put("total", gradeService.getTotal(queryMap));

        return map;
    }

    /**
     * 添加
     *
     * @param grade
     * @return
     */

    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> add(Grade grade) {

        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(grade.getName())) {
            map.put("type", "error");
            map.put("msg", "年级名称不能为空！");
            return map;
        }

        Grade existUser = gradeService.findByName(grade.getName());
        if (existUser != null) {
            map.put("type", "error");
            map.put("msg", "该年级已存在");
            return map;
        }

        gradeService.add(grade);
        map.put("type", "success");
        map.put("msg", "年级添加成功！");

        return map;
    }

    /**
     * 修改
     *
     * @param grade
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(Grade grade) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(grade.getName())) {
            map.put("type", "error");
            map.put("msg", "年级名称不能为空！");
            return map;
        }
        Grade existUser = gradeService.findByName(grade.getName());
        System.out.println(existUser);
        if (existUser != null) {
            map.put("type", "error");
            map.put("msg", "该年级已存在");
            return map;
        }
        gradeService.update(grade);
        map.put("type", "success");
        map.put("msg", "年级修改成功！");
        return map;
    }

    /**
     * 删除
     * @param ids
     * @return
     */
    @RequestMapping(value = "/delete", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> delete(@RequestParam("ids[]") Long[] ids) {
        Map<String, Object> map = new HashMap<>();
        if (ids == null || ids.length == 0) {
            map.put("type", "error");
            map.put("msg", "请选择要删除的数据!");
            return map;
        }
        String idsStr = "";
        for (Long id : ids) {
            idsStr += id + ",";

        }
        idsStr = idsStr.substring(0, idsStr.length() - 1);
        try {
            if(gradeService.delete(idsStr) > 0){
                map.put("type", "error");
                map.put("msg", "删除失败！");
                return map;
            }
        } catch (Exception e) {
            // TODO: handle exception
            map.put("type", "error");
            map.put("msg", "该年级下存在班级信息，请勿冲动！");
            return map;
        }


        map.put("type", "success");
        map.put("msg", "年级删除成功");

        return map;
    }


}
