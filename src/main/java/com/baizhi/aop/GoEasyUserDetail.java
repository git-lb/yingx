package com.baizhi.aop;
import com.alibaba.fastjson.JSONObject;
import com.baizhi.service.back.UserService;
import com.baizhi.vo.UserScattergramVO;
import io.goeasy.GoEasy;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.annotation.Configuration;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;

@Aspect
@Configuration
public class GoEasyUserDetail {

    @Resource
    private UserService userService;

    @After("@annotation(com.baizhi.annotation.GoEasyUserDetail)")
    public void GoEasyUserDetail(){

//当用户发生增删改时  goEasy发送消息通知

        //创建goEasy对象
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io",
                "BC-443efdd7d9bb4fb193ed9223f1837f5f");

//用户详情图推送

        HashMap<String, Object> map = userService.queryBySexAndMonths();

        //将传输的数据转成json格式
        JSONObject jsonObject = new JSONObject();

        jsonObject.put("map", map);

        String content = jsonObject.toString();

        //推送数据
        goEasy.publish("yingx_liub",content);


//用户分布图推送
        List<UserScattergramVO> list = userService.queryBySexAndCity();

        //将传输的数据转成json格式
        JSONObject jsonObjects = new JSONObject();

        jsonObjects.put("list", list);

        String contents = jsonObjects.toString();

        //推送数据
        goEasy.publish("yingx_liub",contents);

    }

}
