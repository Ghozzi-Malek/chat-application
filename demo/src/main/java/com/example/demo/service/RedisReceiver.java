package com.example.demo.service;

import org.springframework.stereotype.Component;

@Component 
public class RedisReceiver {
    public void receiveMessage(String message){
        System.out.println("message: " + message);
    }
}
