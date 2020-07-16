package com.baizhi.vo;

import com.baizhi.po.UserScattergramPO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserScattergramVO implements Serializable {

    private String sex;  //性别
    private List<UserScattergramPO> city;

}
