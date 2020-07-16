package com.baizhi.service.front;

import com.baizhi.common.CommonResult;
import org.springframework.web.multipart.MultipartFile;

public interface AppService {

    CommonResult getPhoneCode(String phone);

    CommonResult queryByReleaseTime();

    CommonResult save(String description, String videoTitle, String categoryId, String userId, MultipartFile videoFile);

    CommonResult queryByVideoDetail(String videoId, String cateId, String userId);
}
