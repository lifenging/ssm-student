package com.gdpi.controller;

import com.gdpi.bean.Clazz;
import com.gdpi.bean.Page;
import com.gdpi.bean.Student;
import com.gdpi.service.ClazzService;
import com.gdpi.service.StudentService;
import net.sf.json.JSONArray;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/student")
public class StudentController {

    @Autowired
    ClazzService clazzService;

    @Autowired
    StudentService studentService;

    /**
     * 学生列表
     *
     * @param model
     * @return
     */
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView model) {
        model.setViewName("student/student_list");
        List<Clazz> list = clazzService.findAll();
        model.addObject("clazzList", list);

        model.addObject("clazzListJson", JSONArray.fromObject(list));
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
                                       @RequestParam(value = "clazzId", required = false) Long clazzId,
                                       HttpServletRequest request,
                                       Page page) {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> queryMap = new HashMap<>();
        queryMap.put("username", "%" + name + "%");
        Object type = request.getSession().getAttribute("userType");
        if ("2".equals(type.toString())){
            //说明是学生
            Student loginStudent = (Student)request.getSession().getAttribute("user");
            queryMap.put("username", "%" + loginStudent.getUsername() + "%");
        }
        if (clazzId != null) {
            queryMap.put("clazzId", clazzId);
        }
        queryMap.put("offset", page.getOffset());
        queryMap.put("pageSize", page.getRows());

        map.put("rows", studentService.findList(queryMap));
        map.put("total", studentService.getTotal(queryMap));

        return map;

    }


    /**
     * 添加
     *
     * @param student
     * @return
     */
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> add(Student student) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(student.getUsername())) {
            map.put("type", "error");
            map.put("msg", "学生姓名不能为空！");
            return map;
        }

        if (StringUtils.isEmpty(student.getPassword())) {
            map.put("type", "error");
            map.put("msg", "登录密码不能为空！");
            return map;
        }

        if (student.getClazzId() == null) {
            map.put("type", "error");
            map.put("msg", "请选择所属班级！");
            return map;
        }


        if (isExist(student.getUsername(),null)) {
            map.put("type", "error");
            map.put("msg", "该学生姓名已存在");
            return map;
        }

        student.setSn("S"+new Date().getTime());

        studentService.add(student);
        map.put("type", "success");
        map.put("msg", "学生添加成功！");

        return map;
    }


    /**
     * 修改
     *
     * @param student
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> update(Student student) {
        Map<String, Object> map = new HashMap<>();
        if (StringUtils.isEmpty(student.getUsername())) {
            map.put("type", "error");
            map.put("msg", "学生姓名不能为空！");
            return map;
        }

        if (StringUtils.isEmpty(student.getPassword())) {
            map.put("type", "error");
            map.put("msg", "登录密码不能为空！");
            return map;
        }

        if (student.getClazzId() == null) {
            map.put("type", "error");
            map.put("msg", "请选择所属班级！");
            return map;
        }


        if (isExist(student.getUsername(),student.getId())) {
            map.put("type", "error");
            map.put("msg", "该学生姓名已存在");
            return map;
        }
        studentService.update(student);
        map.put("type", "success");
        map.put("msg", "学生修改成功！");
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
            if(studentService.delete(idsStr) > 0){
                map.put("type", "error");
                map.put("msg", "删除失败！");
                return map;
            }
        } catch (Exception e) {
            // TODO: handle exception
            map.put("type", "error");
            map.put("msg", "该学生下存在其他信息，请勿冲动！");
            return map;
        }


        map.put("type", "success");
        map.put("msg", "学生删除成功");

        return map;
    }

    /**
     * 用户上传图片
     * @param photo
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    @RequestMapping(value = "/upload_photo",method = RequestMethod.POST)
    @ResponseBody
    public Map<String, Object> uploadPhoto(MultipartFile photo,
                                           HttpServletRequest request,
                                           HttpServletResponse response) throws Exception {

        Map<String, Object> map = new HashMap<>();
        if (photo == null) {
            //文件没有选择
            map.put("type", "error");
            map.put("msg", "请选择文件！");
            return map;
        }
        if (photo.getSize() > 10485760) {

            map.put("type", "error");
            map.put("msg", "文件大小超过10M，请上传小于10M的图片！");
            return map;
        }
        String suffix = photo.getOriginalFilename().substring(photo.getOriginalFilename().lastIndexOf(".") + 1,
                photo.getOriginalFilename().length());
        if (!"jpg,png,gif,jpeg".contains(suffix.toLowerCase())) {
            map.put("type", "error");
            map.put("msg", "文件格式不正确，请上传jpg,png,gif,jpeg格式的图片！");
            return map;
        }

        String savePath = request.getServletContext().getRealPath("/") + "\\upload\\";
       // System.out.println("url:"+savePath);
        File file = new File(savePath);
        if (!file.exists()) {
            file.mkdir();
        }

        String fileName = new Date().getTime() + "." + suffix;
        photo.transferTo(new File(savePath + fileName));
        map.put("type", "success");
        map.put("msg", "图片上传成功！");

        map.put("src", request.getServletContext().getContextPath() + "/upload/" + fileName);
        return map;
    }

    public Boolean isExist(String username ,Long id){
        Student student = studentService.findByName(username);
        if (student!=null){
            if (id==null){
                return true;
            }
            if (student.getId().longValue()!=id.longValue()){
                return true;
            }
        }
        return false;
    }

}
