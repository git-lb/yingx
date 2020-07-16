package com.baizhi.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/*
* 用户详情表参数模型
* */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserPO implements Serializable {
    private String months;
    private String counts;

}
