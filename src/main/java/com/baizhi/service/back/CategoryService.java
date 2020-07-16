package com.baizhi.service.back;

import com.baizhi.entity.Category;

import java.util.HashMap;
import java.util.List;

public interface CategoryService {

    //添加类别
    void addCategory(Category category);

    //分页查询类别
    HashMap<String, Object> findCateByPage(Integer rows, Integer page,String parentId);

    //查询一级类别下的二级类别
    List<Category> findOneCateUnderTwoCate(String id);

    //删除类别
    void deleteCategory(String id);

    //查询所有二级类别
    List<Category> queryAllTwoCategory();
}
