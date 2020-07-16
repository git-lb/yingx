package com.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.annotation.ExcelIgnore;
import cn.afterturn.easypoi.excel.annotation.ExcelTarget;
import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.Column;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;

@ExcelTarget("User")
@Table(name = "yingx_user")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    @ExcelIgnore
    @Id
    private String id;

    @Excel(name = "手机号")
    private String phone;

    @Excel(name = "用户名")
    private String username;


    @Excel(name = "用户头像",type = 2,width = 40 , height = 40)
    @Column(name = "head_img")
    private String headImg;

    @Excel(name = "简介")
    private String brief;

    @Excel(name = "微信")
    private String wechat;

    //@JsonFormat(pattern = "yyyy-MM-dd",timezone = "GMT+8") jackson注解 设置响应的时间格式
    @Excel(name = "注册时间",format = "yyyy-MM-dd")
    @JSONField(format = "yyyy-MM-dd")   //fastjson注解 设置响应的时间格式
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "create_date")
    private Date createDate;

    @Excel(name = "用户状态")
    private String status;

    @Excel(name = "性别")
    private String sex;

    @Excel(name = "城市")
    private String city;


}