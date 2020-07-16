package com.baizhi.entity;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Table(name = "yingx_admin")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Admin implements Serializable {

    @Id
    private Integer id;

    private String username;

    private String password;

    private String status;


}