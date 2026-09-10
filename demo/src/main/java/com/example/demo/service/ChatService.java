package com.example.demo.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import com.example.demo.entities.Chat;
import com.example.demo.entities.Members;
import com.example.demo.entities.ChatMessage;
import com.example.demo.repository.ChatsRepositoryImp;
import com.example.demo.repository.UserRepository;
import com.google.gson.Gson;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.messaging.simp.SimpMessagingTemplate;
@Service
@RequiredArgsConstructor 
public class ChatService {
    private final ChatsRepositoryImp chatsRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;
    private final RedisMessagingService redisMessagingService;

    public List<Chat> getChats(Authentication auth){
        String userId = auth.getName();
        return chatsRepository.getChats(userId);
    }

    public void deliverMessageToChatMembers(ChatMessage message){
        message.setSenderName(userRepository.findById(message.getSenderId()));
        chatsRepository.saveMessageToDb(message);
        List<Members> members = userRepository.ChatsMembers(message);
        for (Members member : members) {

            // from object to json
            Gson gson = new Gson();
            String json = gson.toJson(message);
            redisMessagingService.sendMessage(json,member.getUserId());
        
        }
    }

    public Optional<Chat> joinChat(String chatName, String userId) {
        Chat chat = chatsRepository.findChatByName(chatName);
        if (chat == null) {
            return Optional.empty();
        }

        chatsRepository.addMember(chat.getChatId(), userId);
        return Optional.of(chat);
    }
}
