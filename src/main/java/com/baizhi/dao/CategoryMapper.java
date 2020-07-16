package com.baizhi.dao;
import com.baizhi.entity.Category;
import tk.mybatis.mapper.common.Mapper;

public interface CategoryMapper extends Mapper<Category> {

    //添加类别
    void addCategory(Category category);
}
