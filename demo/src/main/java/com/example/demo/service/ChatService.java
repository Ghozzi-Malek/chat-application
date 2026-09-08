package com.example.demo.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.example.demo.entities.Chat;
import com.example.demo.entities.Members;
import com.example.demo.entities.ChatMessage;
import com.example.demo.repository.ChatsRepositoryImp;
import com.example.demo.repository.UserRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.messaging.simp.SimpMessagingTemplate;
@Service
@RequiredArgsConstructor 
public class ChatService {
    private final ChatsRepositoryImp chatsRepository;
    private final UserRepository userRepository;
    private final SimpMessagingTemplate messagingTemplate;

    public List<Chat> getChats(Authentication auth){
        String userId = auth.getName();
        return chatsRepository.getChats(userId);
    }

    public void deliverMessageToChatMembers(ChatMessage message){
        message.setSenderName(userRepository.findById(message.getSenderId()));
        chatsRepository.saveMessageToDb(message);

        List<Members> members = userRepository.ChatsMembers(message);
        for (Members member : members) {
            messagingTemplate.convertAndSendToUser(
                    member.getUserId(), "/queue/messages", message);
        }

    }
}
