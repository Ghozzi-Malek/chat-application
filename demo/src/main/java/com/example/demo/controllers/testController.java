package com.example.demo.controllers;

import org.apache.logging.log4j.message.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;

import com.example.demo.repository.ChatsRepositoryImp;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.ChatService;
import com.example.demo.service.UserService;
import java.util.List;

import lombok.RequiredArgsConstructor;

import com.example.demo.entities.ChatMessage;
import com.example.demo.entities.MessageTest;
import com.example.demo.entities.Chat;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import java.util.ArrayList;
import java.util.Collections;







@Controller
@RequiredArgsConstructor 
public class testController {

    private final ChatService chatService;
    private final ChatsRepositoryImp chatsRepositoryImp;
    private final SimpMessagingTemplate messagingTemplate;
    @MessageMapping("/chat.send")
    public void sendMessage(ChatMessage message, Authentication auth){
        message.setSenderId(auth.getName());
        message.setTimeStamp(System.currentTimeMillis());
        chatService.deliverMessageToChatMembers(message);
    }

    @MessageMapping("/chat.messages")
    public void getChatMessages(ChatRequest request, Authentication auth){
        List<ChatMessage> messages = chatsRepositoryImp.getChatMessages(request.chatId());
        messages = new ArrayList<>(messages);
        Collections.reverse(messages);
        messagingTemplate.convertAndSendToUser(
                auth.getName(), "/queue/chat-history", messages);
    }

        @MessageMapping("/chat.join")
        public void joinChat(JoinRequest request, Authentication auth) {
        chatService.joinChat(request.chatName(), auth.getName())
            .ifPresentOrElse(
                chat -> messagingTemplate.convertAndSendToUser(
                    auth.getName(), "/queue/chat-joined", chat),
                () -> messagingTemplate.convertAndSendToUser(
                    auth.getName(), "/queue/chat-join-error",
                    "Chat not found"));
        }

    public record ChatRequest(String chatId) {}
        public record JoinRequest(String chatName) {}
    
}
