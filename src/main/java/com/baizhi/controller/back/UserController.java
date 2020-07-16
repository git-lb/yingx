package com.baizhi.controller.back;

import com.baizhi.entity.User;
import com.baizhi.service.back.UserService;
import com.baizhi.vo.UserScattergramVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("backUser")
public class UserController {

    @Autowired
    private UserService userService;

    /*
    * 添加用户
    * */
    @RequestMapping("operateUser")
    @ResponseBody
    public Map<String,Object> operateUser(String oper, User user,String id){

        if("add".equals(oper)){
            Map<String,Object>  map = userService.add(user);
            return map;
        }else  if("del".equals(oper)){
            Map<String,Object> map = userService.deleteUser(id);
            return map;
        }

        return null;
    }

    /*
     * 分页查询数据
     * */
    @RequestMapping("queryByPager")
    @ResponseBody
    public Map<String,Object> queryByPager(Integer rows, Integer page){
        Map<String, Object> map = userService.queryByPager(rows, page);
        return map;
    }

    /*
     * 头像图片的上传
     * */
    @RequestMapping("headUpLoad")
    public void  headUpLoad(String id, MultipartFile headImg){
        //将Json对象转成java对象
        userService.headUpLoad(headImg,id);
    }

    /*
    * 修改用户状态
    * */
    @RequestMapping("updateUserStatus")
    @ResponseBody
    public void updateUserStatus(String id,String status){
        //接收id 和状态参数
        userService.updateUserStatus(id,status);
    }


    /**
     * 发送验证码
     * @param phone
     * @return
     */
    @RequestMapping("sendPhoneCode")
    @ResponseBody
    public String sendPhoneCode(String phone){
        String message = userService.sendPhoneCode(phone);
        return  message;
    }


    /**
     * 导出用户信息
     * @return
     */
    @RequestMapping("outputUserInformation")
    @ResponseBody
    public HashMap<String, Object> outputUserInformation(){
        HashMap<String, Object> map = new HashMap<>();
        String message = userService.outputUserInformation();
        map.put("message",message);
        return map;
    }

    /**
     * 用户详情图数据
     * sql:根据性别和月份查询数据
     */
    @ResponseBody
    @RequestMapping("queryBySexAndMonths")
    public HashMap<String, Object> queryBySexAndMonths(){
        HashMap<String, Object> map = userService.queryBySexAndMonths();
        return map;
    }

    /**
     * 用户分布图
     */
    @ResponseBody
    @RequestMapping("queryBySexAndCity")
    public List<UserScattergramVO> queryBySexAndCity(){

        List<UserScattergramVO> list = userService.queryBySexAndCity();

        return list;

    }
}
