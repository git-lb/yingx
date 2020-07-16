package com.baizhi.po;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * 用户分布图数据分装
 */

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserScattergramPO implements Serializable {

    private String name;
    private String value;

}
