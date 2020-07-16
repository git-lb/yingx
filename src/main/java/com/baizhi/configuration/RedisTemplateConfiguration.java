package com.baizhi.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Configuration
public class RedisTemplateConfiguration {

    /*
     *redis缓存的序列化方式
    */
    @Bean
    public RedisTemplate<String,Object> redisTemplate(RedisConnectionFactory redisConnectionFactory){

        //创建redisTemplate对象
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();

        //赋值工厂类
        redisTemplate.setConnectionFactory(redisConnectionFactory);

        //设置键序列化方式
        redisTemplate.setKeySerializer(new StringRedisSerializer());

        //设置值序列化方式
        redisTemplate.setValueSerializer(new GenericJackson2JsonRedisSerializer());

        return redisTemplate;

    }


}
