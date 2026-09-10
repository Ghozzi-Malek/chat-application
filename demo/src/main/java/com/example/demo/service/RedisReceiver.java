package com.example.demo.service;


import java.nio.charset.StandardCharsets;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.stereotype.Component;
import com.example.demo.entities.ChatMessage;
import org.springframework.messaging.simp.SimpMessagingTemplate;

import com.google.gson.*;

import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor 
public class RedisReceiver implements MessageListener {
    private final Gson gson = new Gson();
    private final SimpMessagingTemplate messagingTemplate;

    @Override
    public void onMessage(Message redisMessage, byte[] pattern) {
        receiveMessage(redisMessage);
    }
        
    public void receiveMessage(Message redisMessage){
        String json = new String(redisMessage.getBody(), StandardCharsets.UTF_8);
        String channel = new String(redisMessage.getChannel(), StandardCharsets.UTF_8);
        String userId = channel.substring("chat:user:".length());
        ChatMessage message = gson.fromJson(json, ChatMessage.class);
        messagingTemplate.convertAndSendToUser(
                    userId, "/queue/messages", message);
        
        System.out.println("message: " + message);
    }
}
