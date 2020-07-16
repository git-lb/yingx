package com.baizhi.dao;

import com.baizhi.entity.User;
import com.baizhi.po.UserPO;
import com.baizhi.po.UserScattergramPO;
import org.apache.ibatis.annotations.Param;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface UserMapper extends Mapper<User> {
    //添加用户
    void add(User user);

    //分页查询
    List<User> queryByPager(@Param("start") Integer start, @Param("size") Integer rows);

    //用户详情图
    List<UserPO> queryBySexAndMonths(String sex);

    //用户分布图
    List<UserScattergramPO> queryBySexAndCity(String sex);
}