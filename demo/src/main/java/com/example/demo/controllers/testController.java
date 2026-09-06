package com.example.demo.controllers;

import org.apache.logging.log4j.message.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import com.example.demo.repository.ChatsRepositoryImp;
import com.example.demo.service.ChatService;
import com.example.demo.service.RedisMessagingService;

import lombok.RequiredArgsConstructor;

import com.example.demo.entities.ChatMessage;
import com.example.demo.entities.MessageTest;







@Controller
@RequiredArgsConstructor 
public class testController {

    private final ChatService chatService;
    private final RedisMessagingService messagingService;
    @MessageMapping("/chat.send")
    @SendTo("/topic/messages")

    public void sendMessage(ChatMessage message){
        chatService.deliverMessage(message);
        messagingService.sendMessage(message.getText());
    }
    
}
