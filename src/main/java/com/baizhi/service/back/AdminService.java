package com.baizhi.service.back;

import com.baizhi.entity.Admin;

public interface AdminService {
    Admin queryByUserName(String username);
}
