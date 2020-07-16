package com.baizhi.serviceImpl.back;
import com.baizhi.dao.AdminMapper;
import com.baizhi.entity.Admin;
import com.baizhi.service.back.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {

    @Autowired
    private AdminMapper adminMapper;

    @Transactional(propagation = Propagation.SUPPORTS)
    public Admin queryByUserName(String username) {
        //使用MBG插件生成的查询方法
        Admin adminExample = new Admin();
        adminExample.setUsername(username);
        //根据条件查询出一条数据
        Admin admin = adminMapper.selectOne(adminExample);
        //返回前台数据 用作判断
        if(admin == null){
           return null;
        }else{
           return admin;
        }
    }
}
