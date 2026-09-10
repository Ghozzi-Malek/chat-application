package com.example.demo.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

@Component 
public class RedisMessagingService {

    @Autowired  
    private StringRedisTemplate redisTemplate;

    public void sendMessage(String message,String channel){
        redisTemplate.convertAndSend("chat:user:" + channel, message);
    }
}
