package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component 
public class RedisMessagingService {

    @Autowired  
    private StringRedisTemplate redisTemplate;
    @Autowired 
    private String redisChannel;

    public void sendMessage(String message){
        redisTemplate.convertAndSend("malek", message);
    }
}
