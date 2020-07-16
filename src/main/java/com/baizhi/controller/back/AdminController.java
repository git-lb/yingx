package com.baizhi.controller.back;
import com.baizhi.entity.Admin;
import com.baizhi.service.back.AdminService;
import com.baizhi.util.ImageCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("backAdmin")
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private HttpServletResponse response;

    @Autowired
    private HttpServletRequest request;

    //验证码 (点击更换验证码)
    @RequestMapping("getImageCode")
    @ResponseBody
    public String getImageCode(){
        //获取验证码
        String securityCode = ImageCodeUtil.getSecurityCode();
        //将验证码存到session中 以便于登录时做验证
        HttpSession session = request.getSession();
        session.setAttribute("systemSecurityCode",securityCode);
        //返回带有验证码的图片给前台
        BufferedImage image = ImageCodeUtil.createImage(securityCode);
        try {
            //获取输出流
            ServletOutputStream outputStream = response.getOutputStream();
            //ImageIO类  "png" 图片输出格式
            ImageIO.write(image,"png",outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /*
    * 后台登录功能
    * */
    @RequestMapping("login")
    @ResponseBody
    public Map<String, String> login(String username, String password,  String enCode){

        Map<String, String> map = new HashMap<>();
        HttpSession session = request.getSession();
        //验证验证码是否输入正确
        if(enCode.equals(session.getAttribute("systemSecurityCode"))){
            //验证码正确 调用业务层方法
            Admin admin = adminService.queryByUserName(username);
            if(admin == null){
                map.put("msg","用户名输入错误!");
                return map;
            }else{
                if(admin.getPassword().equals(password)){
                    map.put("msg","success");
                    session.setAttribute("admin",admin);
                    return map;
                }else {
                    map.put("msg","密码输入错误!");
                    return map;
                }

            }
        }else {
           map.put("msg","验证码错误!");
           return map;
        }
    }

    /*
    * 安全退出功能   注意：使用请求转发的方式  跳转页面出错
    * */

    @RequestMapping("logout")
    public String logout(){
        //不能直接销毁session 不合理
        /*销毁session
        session.invalidate();*/
        //将存储的用户信息从session中移除
        request.getSession().removeAttribute("admin");
        return "redirect:/login/login.jsp";
    }



}
