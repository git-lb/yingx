package com.baizhi.service.back;

import java.util.Map;

public interface LogService {

    Map<String, Object> queryAllLogByPage(Integer rows, Integer page);
}
