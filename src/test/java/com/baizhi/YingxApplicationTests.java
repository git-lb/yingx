package com.baizhi;

import com.baizhi.dao.*;
import com.baizhi.entity.Category;
import com.baizhi.entity.Feedback;
import com.baizhi.po.UserScattergramPO;
import com.baizhi.util.UUIDUtil;
import io.goeasy.GoEasy;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;


@RunWith(SpringRunner.class)
@SpringBootTest
public class YingxApplicationTests {

    @Autowired
    private AdminMapper adminMapper;

    @Autowired
    private UserMapper userMapper;

    @Autowired
    private CategoryMapper categoryMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Resource
    private FeedbackMapper feedbackMapper;

    @Resource
    private RedisTemplate redisTemplate;
    @Test
    public void bucketTest(){
       /* String a = "1 1 1";
        boolean contains = a.contains(" ");
        System.out.println(contains);

        String replace = a.replace(" ", "");
        System.out.println(replace);
        System.out.println(a);*/
       // https://yingx-liub.oss-cn-qingdao.aliyuncs.com/photo/1594124465339-timgOBS8CQSB.jpg
       /* String newFileName = "文件名";
        String catalogue = "photo/";
        String networkFileName = "http://oss-cn-qingdao.aliyuncs.com/";

        long l = System.currentTimeMillis();
        System.out.println(l);

        String neturl = networkFileName+l+"-"+newFileName;
        System.out.println(neturl);*/
/*
        String endpoint = "http://oss-cn-qingdao.aliyuncs.com";
        String catalogue = "photo";
        String newFileName = "图片";
        long l = System.currentTimeMillis();



        String networkFileName = endpoint+"/"+catalogue+"/"+l+"-"+newFileName;

        System.out.println(networkFileName);*/
        int a = 5;
        int c = a--;
        int d = a++;
        int f = ++a;
        int b =(a++)+(++a)+(a--);
        System.out.println(a);
        System.out.println(b);
        System.out.println(c);
        System.out.println(d);
        System.out.println(f);

    }

    @Test
    public void test2(){

        Feedback feedback = new Feedback();
        feedback.setId(UUIDUtil.getUUID());
        feedback.setContent("1");
        feedback.setTitle("1");
        feedback.setFeedbackDate(new Date());
        feedback.setUserId("1");
        feedbackMapper.insertSelective(feedback);
    }
    @Test
    public void test3(){
        GoEasy goEasy = new GoEasy( "http://rest-hangzhou.goeasy.io",
                "BC-443efdd7d9bb4fb193ed9223f1837f5f");

                goEasy.publish("yingx_liub", "Hello, GoEasy!");


    }
    @Test
    public void test4(){
        String headImag = "https://yingx-liub.oss-cn-qingdao.aliyuncs.com/userHeadImg/1594396836437-timgP30XGOPL.jpg";
        String fileName=headImag.substring(headImag.lastIndexOf("m/")+2);
        System.out.println(fileName);

    }

    @Test
    public void test5() {

        List<UserScattergramPO> man = userMapper.queryBySexAndCity("男");
        List<UserScattergramPO> woman = userMapper.queryBySexAndCity("女");

        for (UserScattergramPO userScattergramPO : man) {
            System.out.println("======"+userScattergramPO);
        }

        System.out.println("-----------------------------------------------------");

        for (UserScattergramPO wwww : woman) {
            System.out.println("-------"+wwww);
        }

    }

    @Test
    public void test6() {
        HashOperations hashOperations = redisTemplate.opsForHash();
        /*hashOperations.put("大标题","小标题","值");
        Category category = new Category();
        category.setLevels("1");
        category.setCateName("类别");
        category.setId("1");
        category.setParentId("1");
        hashOperations.put("大标题","小标题",category);*/


        Category o = (Category) hashOperations.get("大标题", "小标题");
        System.out.println(o.getClass());
        System.out.println(o.toString());
        System.out.println(o);
        Category category = new Category();
        category.setLevels("1");
        category.setCateName("类别");
        category.setId("1");
        category.setParentId("1");
        System.out.println("========"+category);

    }

}
