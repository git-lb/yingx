package com.baizhi.app;
import com.baizhi.common.CommonResult;
import com.baizhi.service.front.AppService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

@Controller
@RequestMapping("app")
public class AppController {

    @Resource
    private AppService appService;


    /**
     * 用户登录注册
     * 1.发送验证码
     * @param phone :手机号 string
     */
    @ResponseBody
    @RequestMapping("getPhoneCode")
    public CommonResult getPhoneCode(String phone){
        //调用业务层方法
        CommonResult commonResult = appService.getPhoneCode(phone);
        return commonResult;
    }


    /**
     * 首页展示视频:根据时间排序查询所有视频数据
     * @return
     * HashMap  data:集合对象  message  status:
     */
    @ResponseBody
    @RequestMapping("queryByReleaseTime")
    public CommonResult queryByReleaseTime(){
        //调用业务层方法
        CommonResult commonResult =appService.queryByReleaseTime();
        return commonResult;
    }


    /**
     * 用户发布视频
     * @return
     */
    @ResponseBody
    @RequestMapping("save")
    public CommonResult save(String description, String videoTitle, String categoryId, String userId, MultipartFile videoFile){
        //调用业务层方法
        CommonResult commonResult =appService.save(description,videoTitle,categoryId,userId,videoFile);
        return commonResult;
    }


    /**
     *视频详情信息
     *内容包括：
     *视频的基本信息
     *相同类别视频的推介
     *关注表中是否关注该用户
     *此视频点赞数
     *此视频评论的条数
     * @param videoId ：视频id
     * @param cateId :类别id
     * @param userId :用户id
     * @return
     */
    @ResponseBody
    @RequestMapping("queryByVideoDetail")
    public CommonResult queryByVideoDetail(String videoId,String cateId,String userId){
        CommonResult commonResult = appService.queryByVideoDetail(videoId,cateId,userId);
        return commonResult;
    }


}
