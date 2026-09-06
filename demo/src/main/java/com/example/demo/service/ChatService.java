package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.demo.entities.Chat;
import com.example.demo.entities.ChatMessage;
import com.example.demo.repository.ChatsRepositoryImp;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
@Service
@RequiredArgsConstructor 
public class ChatService {
    private final ChatsRepositoryImp chatsRepository;
    private final RedisMessagingService redisMessagingService;

    public List<Chat> getChats(Authentication auth){
        String userId = auth.getName();
        return chatsRepository.getChats(userId);
    }
    public void deliverMessage(ChatMessage message) {
        
        chatsRepository.saveMessageToDb(message);
        redisMessagingService.sendMessage(message.getText());

        
    }
}
