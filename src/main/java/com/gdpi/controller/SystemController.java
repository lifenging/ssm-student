package com.gdpi.controller;

import com.gdpi.bean.Student;
import com.gdpi.bean.User;
import com.gdpi.service.StudentService;
import com.gdpi.service.UserService;
import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping(value = "/system")
public class SystemController {

    @Autowired
    UserService userService;

    @Autowired
    StudentService studentService;

    private Producer kaptchaProducer;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public ModelAndView index(ModelAndView model){
        model.setViewName("sys/index");
        return model;
    }
    /**
     * 登录页面
     * @param model
     * @return
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(ModelAndView model){
        model.setViewName("sys/login");
        return model;
    }

    /**
     * 注销登录
     * @param request
     * @return
     */
    @RequestMapping(value = "/login_out",method = RequestMethod.GET)
    public String loginOut(HttpServletRequest request){
        request.getSession().setAttribute("user",null);
        return "redirect:login";
    }


    /**
     * 登录表单提交
     * @param username
     * @param password
     * @param vcode
     * @param type
     * @param request
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/to_login", method = RequestMethod.POST)
    public Map<String, Object> login(@RequestParam("username") String username,
                                     @RequestParam("password") String password,
                                     @RequestParam("vcode") String vcode,
                                     @RequestParam("type") int type,
                                     HttpServletRequest request){

        Map<String, Object> map = new HashMap();
       if (StringUtils.isEmpty(username)){
           map.put("type","error");
           map.put("msg","用户名不能为空");
           return map;
       }
        if (StringUtils.isEmpty(password)){
            map.put("type","error");
            map.put("msg","密码不能为空");
            return map;
        }
        if (StringUtils.isEmpty(vcode)){
            map.put("type","error");
            map.put("msg","验证码不能为空");
            return map;
        }
        String loginCpacha = (String)request.getSession().getAttribute(Constants.KAPTCHA_SESSION_KEY);
        if (StringUtils.isEmpty(loginCpacha)){
            map.put("type","error");
            map.put("msg","长时间未操作，会话失效");
            return map;
        }

        if (!vcode.toUpperCase().equals(loginCpacha.toUpperCase())){
            map.put("type","error");
            map.put("msg","验证码错误");
            return map;
        }
        request.getSession().setAttribute("loginCpacha",null);


        if (type==1){
            //管理员
            User user = userService.findByUserName(username);

                if (user==null){
                    map.put("type","error");
                    map.put("msg","不存在该用户");
                    return map;
                }
                if (!password.equals(user.getPassword())){
                    map.put("type","error");
                    map.put("msg","密码错误！");
                    return map;
                }
                request.getSession().setAttribute("user",user);
            }
        if (type==2){
            //学生
            Student student = studentService.findByName(username);
            if (student==null){
                map.put("type","error");
                map.put("msg","不存在该学生");
                return map;
            }
            if (!password.equals(student.getPassword())){
                map.put("type","error");
                map.put("msg","密码错误");
                return map;
            }
            request.getSession().setAttribute("user",student);
        }
        request.getSession().setAttribute("userType",type);
        map.put("type","success");
        map.put("msg","登录成功");
        return map;
    }

    /**
     * 验证码
     * @param kaptchaProducer
     */

    @Autowired
    public void setKaptchaProducer(Producer kaptchaProducer) {
        this.kaptchaProducer = kaptchaProducer;
    }

    @RequestMapping(value = "/get_cpacha", method = RequestMethod.GET)
    public void getCpacha(HttpServletRequest request, HttpServletResponse response){
        // 禁止server端缓存
        response.setDateHeader("Expires",0);
        // 设置标准的 HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");
        // 设置IE扩展 HTTP/1.1 no-cache headers (use addHeader)
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");
        // 设置标准 HTTP/1.0 不缓存图片
        response.setHeader("Pragma", "no-cache");
        // 返回一个 jpeg 图片，默认是text/html(输出文档的MIMI类型)
        response.setContentType("image/jpeg");
        // 为图片创建文本
        String capText = kaptchaProducer.createText();
        // 将文本保存在session中。这里就使用包中的静态变量吧
        request.getSession().setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        // 创建带有文本的图片
        BufferedImage bi = kaptchaProducer.createImage(capText);
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
            // 图片数据输出
            ImageIO.write(bi, "jpg", out);
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }


}
