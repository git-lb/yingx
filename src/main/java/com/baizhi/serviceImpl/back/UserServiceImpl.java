package com.baizhi.serviceImpl.back;
import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.baizhi.annotation.AddCache;
import com.baizhi.annotation.AddLog;
import com.baizhi.annotation.DelCache;
import com.baizhi.annotation.GoEasyUserDetail;
import com.baizhi.dao.UserMapper;
import com.baizhi.entity.User;
import com.baizhi.entity.UserExample;
import com.baizhi.po.UserPO;
import com.baizhi.po.UserScattergramPO;
import com.baizhi.service.back.UserService;
import com.baizhi.util.AliyunBucketOSSUtil;
import com.baizhi.util.DeleteFolderAndFile;
import com.baizhi.util.SendSmsCode;
import com.baizhi.util.UUIDUtil;
import com.baizhi.vo.UserScattergramVO;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;


    //发送验证码
    public String sendPhoneCode(String phone) {
        //调用发送短信验证码的工具类
        String numberCode = SendSmsCode.getNumberCode(4);
        String message = SendSmsCode.sendSmsCodeToPhone(phone,numberCode);
        return message;
    }


    //导出用户信息
    public String outputUserInformation() {
        try {
            //查询所有用户信息
            List<User> list = userMapper.selectAll();
            //创建本地文件夹
            File file = new File("src/main/webapp/upload");
            if(!file.exists()){
                file.mkdirs();
            }
            String path = "src/main/webapp/upload";
            String downPath = null;
            //截取用户头像图片名
            for (User user : list) {
                String headImg = user.getHeadImg();
                String fileName=headImg.substring(headImg.lastIndexOf("m/")+2);
                downPath=path+"/"+headImg.substring(headImg.lastIndexOf("mg/")+2);
                //下载用户头像到本地
                AliyunBucketOSSUtil.downloadFile(fileName,downPath);
                //给用户头像字段赋值本地路径
                user.setHeadImg(downPath);
            }
            //EsayPOI导出用户信息到本地
            ExportParams exportParams = new ExportParams("用户基本信息", "应学用户表");
            Workbook workbook = ExcelExportUtil.exportExcel( exportParams,User.class, list);
            FileOutputStream fileOutputStream = new FileOutputStream(new File("D:\\EndItem\\应学用户表.xls"));
            workbook.write(fileOutputStream);
            fileOutputStream.close();
            workbook.close();
            //将下载的文件删除
            DeleteFolderAndFile.delAllFile(path);  //删除文件夹下的文件
            DeleteFolderAndFile.delFolder(path);   //删除空文件夹
            return "导出成功,请到D:/EndItem目录下查收";
        } catch (IOException e) {
            e.printStackTrace();
            return "导出失败";
        }

    }


    //添加用户信息
    @DelCache
    @GoEasyUserDetail
    @AddLog("添加用户")
    public Map<String,Object> add(User user) {
        String uuid = null;
        HashMap<String, Object> map = new HashMap<>();
        try {
            //给用户的注册时间进行赋值
            Date date = new Date();
            user.setCreateDate(date);
            //获取uuid给用户的id进行赋值
            uuid = UUIDUtil.getUUID();
            user.setId(uuid);
            //给用户状态赋值
            user.setStatus("1");
            userMapper.add(user);

            map.put("id",uuid);
            map.put("msg","添加成功");
            return map;
        } catch (Exception e) {
            map.put("mes","添加失败");
            e.printStackTrace();
            return map;
        }
    }

    //用户分页查询数据展示
    @AddCache
    public Map<String, Object> queryByPager(Integer rows, Integer page) {
        /*
         * rows:每页展示几条数据
         * page:当前页数
         * */
        HashMap<String, Object> map = new HashMap<>();
        //查询数据总条数
        User user = new User();
        int counts = userMapper.selectCount(user);
        //计算起始下标
        Integer start = (page-1)*rows;
        //计算总页数
        Integer total = counts%rows == 0?counts/rows:counts/rows+1;
        //分页查询
        List<User> list = userMapper.queryByPager(start,rows);
        //返回的数据
        map.put("rows",list);
        map.put("total",total);
        map.put("page",page);
        map.put("records",counts);
        return map;
    }

    //用户头像的上传
    @DelCache
    public void headUpLoad(MultipartFile headImg, String id) {
        //文件上传
        //获取上传文件的byte字节数组
        byte[] bytes = null;
        try {
            bytes = headImg.getBytes();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //获取文件名
        String headImgName = headImg.getOriginalFilename();

        //调用阿里云工具类
        String userHeadImg = AliyunBucketOSSUtil.fileByteUpload("userHeadImg", headImgName, bytes);

        //修改原数据的图片路径
        //将需要修改的数据 封装到user对象
        User user = new User();
        user.setHeadImg(userHeadImg);

        //将根据什么修改的数据  封装到userExample对象
        UserExample userExample = new UserExample();
        userExample.createCriteria().andIdEqualTo(id);
        userMapper.updateByExampleSelective(user,userExample);
    }

    //修改用户状态
    @DelCache
    @GoEasyUserDetail
    @AddLog("编辑用户")
    public void updateUserStatus(String id, String status) {
        User user = new User();
        UserExample userExample = new UserExample();
        if("1".equals(status)){
            //状态==1  修改为2
            //将需要修改的条件存到user
            user.setStatus("2");
            //将根据什么修改存到userExample
            userExample.createCriteria().andIdEqualTo(id);
            //调用通用Mapper的方法
            userMapper.updateByExampleSelective(user, userExample);
        }else{
            //状态不==1
            user.setStatus("1");
            //将根据什么修改存到userExample
            userExample.createCriteria().andIdEqualTo(id);
            //调用通用Mapper的方法
            userMapper.updateByExampleSelective(user, userExample);
        }
    }

    //用户详情图
    @AddCache
    @Transactional(propagation = Propagation.SUPPORTS)
    public HashMap<String, Object> queryBySexAndMonths() {

        //性别集合
        ArrayList<String> sexs = new ArrayList<>();
        sexs.add("男");
        sexs.add("女");

        //查询男生的数据
        List<UserPO> manList  = userMapper.queryBySexAndMonths(sexs.get(0));
        //查询女生的数据
        List<UserPO> womanList  = userMapper.queryBySexAndMonths(sexs.get(1));

//男生操作
        //数据存储到map
        HashMap<String, String> manMap = new HashMap<>();
        //赋值
        for (UserPO userPO : manList) {
            manMap.put(userPO.getMonths(),userPO.getCounts());
        }
        //返回男生数据集合
        ArrayList<String> manCount = new ArrayList<>();
        Set<String> mankeySet = manMap.keySet();

        for (int i = 1; i <= 12; i++) {
            if(mankeySet.contains(i+"月")){
                manCount.add(manMap.get(i+"月"));
            }else{
                manCount.add("0");
            }
        }
//女生操作
        //数据存储到map
        HashMap<String, String> womanMap = new HashMap<>();
        //赋值
        for (UserPO userPO : womanList) {
            womanMap.put(userPO.getMonths(),userPO.getCounts());
        }
        //返回女生数据集合
        ArrayList<String> womanCount = new ArrayList<>();
        Set<String> womanKeyset = womanMap.keySet();

        for (int i = 1; i <= 12; i++) {
            if(womanKeyset.contains(i+"月")){
                womanCount.add(womanMap.get(i+"月"));
            }else{
                womanCount.add("0");
            }
        }

        List<String> months = Arrays.asList("1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月");

        HashMap<String, Object> resultMap = new HashMap<>();
        resultMap.put("man",manCount);
        resultMap.put("woman",womanCount);
        resultMap.put("months",months);
        resultMap.put("sex",sexs);

        return resultMap;
    }


    //删除用户
    @DelCache
    @GoEasyUserDetail
    public Map<String, Object> deleteUser(String id) {

        HashMap<String, Object> map = new HashMap<>();

        try {
            //根据id查出用户的头像信息
            User user = userMapper.selectByPrimaryKey(id);

            //根据id删除用户
            userMapper.deleteByPrimaryKey(id);

            //用户的头像地址
            String headImg = user.getHeadImg();

            //截取路径
            String fileName=headImg.substring(headImg.lastIndexOf("m/")+2);

            //将用户的头像删除
            AliyunBucketOSSUtil.deleteFile(fileName);

            //返回数据
            map.put("mes","删除成功");
            return map;
        } catch (Exception e) {
            map.put("msg","删除失败");
            e.printStackTrace();
            return map;
        }

    }

    //用户分布图查询
    @AddCache
    @Transactional(propagation = Propagation.SUPPORTS)
    public List<UserScattergramVO> queryBySexAndCity() {
        //根据性别查询用户的所在城市和人数

        String[] sexs = {"男","女"};

        //男生集合
        List<UserScattergramPO> manList = userMapper.queryBySexAndCity(sexs[0]);

        //女生集合
        List<UserScattergramPO> womenList = userMapper.queryBySexAndCity(sexs[1]);

        //返回userVo数据
        UserScattergramVO man  = new UserScattergramVO("男",manList);

        UserScattergramVO women = new UserScattergramVO("女", womenList);

        ArrayList<UserScattergramVO> resultList = new ArrayList<>();

        resultList.add(man);
        resultList.add(women);

        return resultList;
    }
}
