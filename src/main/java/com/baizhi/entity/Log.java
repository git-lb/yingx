package com.baizhi.entity;
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

@Table(name = "yingx_log")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Log implements Serializable {
    @Id
    private String id;

    @Column(name = "admin_name")
    private String adminName;

    @JSONField(format = "yyyy-MM-dd")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "oper_date")
    private Date operDate;

    private String brief;

    private String status;


}