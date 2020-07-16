package com.baizhi.controller.back;

import com.baizhi.service.back.LogService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.Map;

@Controller
@RequestMapping("backLog")
public class LogController {

    @Resource
    private LogService logService;


    /**
     * 分页查询所有数据
     * @param rows
     * @param page
     * @return
     */
    @RequestMapping("queryAllLogByPage")
    @ResponseBody
    public Map<String,Object> queryAllLogByPage(Integer rows, Integer page){
        Map<String,Object> map = logService.queryAllLogByPage(rows,page);
        return map;
    }
}
