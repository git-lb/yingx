package com.baizhi.aop;


import com.baizhi.annotation.AddCache;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;

import javax.annotation.Resource;
import java.lang.reflect.Method;


/*
* redis缓存 存储经常查询的数据
*
* */


@Aspect
@Configuration
public class RedisCacheAspect {

    @Resource
    private RedisTemplate redisTemplate;

    /**
     * 添加缓存
     * @param proceedingJoinPoint
     * @return
     */
    @Around("@annotation(com.baizhi.annotation.AddCache)")
    public Object addCache(ProceedingJoinPoint proceedingJoinPoint){
        /*
        * 缓存存储的key设计必须唯一
        * KEY:类的全限定名
        * key:方法名+实参(形参的值)  hash数据类型存储(删除缓存时代码优化)
        * */

        /*解决缓存乱码*/
      /*  StringRedisSerializer stringRedisSerializer = new StringRedisSerializer();
        redisTemplate.setKeySerializer(stringRedisSerializer);
        redisTemplate.setHashKeySerializer(stringRedisSerializer);*/



        //判断方法是否有注解
        MethodSignature signature = (MethodSignature) proceedingJoinPoint.getSignature();
        Method method = signature.getMethod();
        boolean annotationPresent = method.isAnnotationPresent(AddCache.class);

        //有缓存注解
        if(annotationPresent){

            //获取访问方法类的全限定名
            String clazzName = proceedingJoinPoint.getTarget().getClass().getName();

            //创建一个可变长字符串
            StringBuilder stringBuilder = new StringBuilder();

            //获取方法名
            String methodName = proceedingJoinPoint.getSignature().getName();
            stringBuilder.append(methodName);

            //获取实参
            Object[] args = proceedingJoinPoint.getArgs();

            //拼接参数
            for (Object arg : args) {
                stringBuilder.append(arg);
            }

            //作为hash中的小key
            String key = stringBuilder.toString();

            //判断缓存中是否有访问的数据
            HashOperations hashOperations = redisTemplate.opsForHash();

            Boolean a = hashOperations.hasKey(clazzName,key);

            Object value =null;

            try {

                if(!a){
                    //没有缓存 放行 返回值是方法的返回值
                    value = proceedingJoinPoint.proceed();

                    //将返回的结果 存储到缓存
                    hashOperations.put(clazzName,key,value);

                }else {

                    //有缓存 直接取出返回
                    value = hashOperations.get(clazzName, key);

                }

            } catch (Throwable throwable) {

                throwable.printStackTrace();

            }finally {

                return value;
            }
        }else {
            //没有注解 直接放行
            try {
                Object proceed = proceedingJoinPoint.proceed();
                return proceed;
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            return null;
        }
    }


    /**
     * 删除缓存 数据增删改的时候都要删除缓存
     * @param joinPoint
     */
    @After("@annotation(com.baizhi.annotation.DelCache)")
    public void delCache(JoinPoint joinPoint){

        //获取方法的类的全限定名
        String clazzName = joinPoint.getTarget().getClass().getName();

        //删除KEY下的所有缓存
        redisTemplate.delete(clazzName);


    }



}
