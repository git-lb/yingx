package com.baizhi.serviceImpl.back;

import com.baizhi.dao.LogMapper;
import com.baizhi.entity.Log;
import com.baizhi.service.back.LogService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class LogServiceImpl implements LogService {

    @Resource
    private LogMapper logMapper;



    @Transactional(propagation = Propagation.SUPPORTS)
    public Map<String, Object> queryAllLogByPage(Integer rows, Integer page) {

        HashMap<String, Object> map = new HashMap<>();
        Log log = new Log();

        //查询视频总条数
        int counts = logMapper.selectCount(log);

        //计算总页数
        Integer total = counts%rows == 0?counts/rows:counts/rows+1;

        //分页查询
        RowBounds rowBounds = new RowBounds((page-1)*rows,rows);
        List<Log> list = logMapper.selectByRowBounds(log, rowBounds);

        //返回数据
        map.put("page",page);  //当前页数
        map.put("list",list);  //查询的数据
        map.put("records",counts); //数据总条数
        map.put("total",total);   //数据总页数

        return map;
    }
}
