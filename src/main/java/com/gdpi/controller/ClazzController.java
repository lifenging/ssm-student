package com.gdpi.controller;

import com.gdpi.bean.Clazz;
import com.gdpi.bean.Grade;
import com.gdpi.bean.Page;
import com.gdpi.service.ClazzService;
import com.gdpi.service.GradeService;
import net.sf.json.JSONArray;
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
@RequestMapping("/clazz")
public class ClazzController {

    @Autowired
    GradeService gradeService;

    @Autowired
    ClazzService clazzService;


    /**
     * 班级列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView model) {
        model.setViewName("clazz/clazz_list");
        List<Grade> list = gradeService.findAll();
        model.addObject("gradeList", list);

        model.addObject("gradeListJson", JSONArray.fromObject(list));
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
                                       @RequestParam(value = "gradeId", required = false) Long gradeId,
                                       Page page) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("name", "%" + name + "%");
        if (gradeId != null) {
            queryMap.put("gradeId", gradeId);
        }
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());

        map.put("rows", clazzService.findList(queryMap));
        map.put("total", clazzService.getTotal(queryMap));

        return map;
    }


    /**
     * 添加
     *
     * @param clazz
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> add(Clazz clazz) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(clazz.getName())) {
            map.put("type", "error");
            map.put("msg", "班级名称不能为空！");
            return map;
        }

        if (clazz.getGradeId() == null) {
            map.put("type", "error");
            map.put("msg", "请选择所属年级！");
            return map;
        }

        Clazz existUser = clazzService.findByName(clazz.getName());
        if (existUser != null) {
            map.put("type", "error");
            map.put("msg", "该班级已存在");
            return map;
        }

        clazzService.add(clazz);
        map.put("type", "success");
        map.put("msg", "班级添加成功！");

        return map;
    }

    /**
     * 修改
     *
     * @param clazz
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(Clazz clazz) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(clazz.getName())) {
            map.put("type", "error");
            map.put("msg", "班级名称不能为空！");
            return map;
        }
        if (clazz.getGradeId() == null) {
            map.put("type", "error");
            map.put("msg", "所属年级不能为空！");
            return map;
        }
        Clazz existUser = clazzService.findByName(clazz.getName());
        if (existUser != null) {
            map.put("type", "error");
            map.put("msg", "该班级已存在");
            return map;
        }
        clazzService.update(clazz);
        map.put("type", "success");
        map.put("msg", "班级修改成功！");
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
            if(clazzService.delete(idsStr) > 0){
                map.put("type", "error");
                map.put("msg", "删除失败！");
                return map;
            }
        } catch (Exception e) {
            // TODO: handle exception
            map.put("type", "error");
            map.put("msg", "该班级下存在学生信息，请勿冲动！");
            return map;
        }


        map.put("type", "success");
        map.put("msg", "班级删除成功");

        return map;
    }
}
