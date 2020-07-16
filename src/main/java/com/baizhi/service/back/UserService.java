package com.baizhi.service.back;

import com.baizhi.entity.User;
import com.baizhi.vo.UserScattergramVO;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface UserService {

    //添加用户
    Map<String, Object> add(User user);

    //分页查询 展示用户
    Map<String,Object> queryByPager(Integer rows, Integer page);

    //头像上传
    void headUpLoad(MultipartFile headImg, String id);

    //修改用户状态
    void updateUserStatus(String id,String status);

    //发送短信验证码
    String sendPhoneCode(String phone);

    //导出用户信息
    String outputUserInformation();

    //用户详情图
    HashMap<String, Object> queryBySexAndMonths();

    //删除用户
    Map<String, Object> deleteUser(String id);

    //用户分布图
    List<UserScattergramVO> queryBySexAndCity();
}
