package com.baizhi.common;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/***
 * 通用返回结果封装
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommonResult {

    private Object data;
    private String message;
    private String status;

}
