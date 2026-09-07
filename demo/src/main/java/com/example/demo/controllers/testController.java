package com.example.demo.controllers;

import org.apache.logging.log4j.message.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import com.example.demo.repository.ChatsRepositoryImp;
import com.example.demo.service.ChatService;

import lombok.RequiredArgsConstructor;

import com.example.demo.entities.ChatMessage;
import com.example.demo.entities.MessageTest;







@Controller
@RequiredArgsConstructor 
public class testController {

    private final ChatService chatService;
    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessage message,Authentication auth){
        message.setSenderId(auth.getName());
        message.setTimeStamp(System.currentTimeMillis());
        chatService.deliverMessageToChatMembers(message);
    }
    
}
