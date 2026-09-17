package com.example.demo.service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

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

    public ChatGroups getChatGroups(Authentication auth){
        String userId = auth.getName();
        List<Chat> allChats = chatsRepository.getChats(userId);
        List<String> missedChatIds = chatsRepository.getMissedChats(userId);

        Map<String, Chat> chatsById = new HashMap<>();
        for (Chat chat : allChats) {
            chatsById.put(chat.getChatId(), chat);
        }

        List<Chat> missedChats = new java.util.ArrayList<>();
        for (String missedChatId : missedChatIds) {
            Chat chat = chatsById.get(missedChatId);
            if (chat != null) {
                missedChats.add(chat);
            }
        }

        Set<String> missedIds = new HashSet<>(missedChatIds);
        List<Chat> otherChats = allChats.stream()
                .filter(chat -> !missedIds.contains(chat.getChatId()))
                .toList();
        
        chatsRepository.deleteMissedMessages(userId);

        return new ChatGroups(missedChats, otherChats);
    }

    public record ChatGroups(List<Chat> missedChats, List<Chat> otherChats) {}

    public void deliverMessageToChatMembers(ChatMessage message){
        message.setMessageId(UUID.randomUUID().toString());
        message.setSenderName(userRepository.findById(message.getSenderId()));
        chatsRepository.saveMessageToDb(message);
        List<Members> members = userRepository.ChatsMembers(message);
        for (Members member : members) {

            // from object to json
            Gson gson = new Gson();
            String json = gson.toJson(message);
                chatsRepository.saveMissingMessage(member.getUserId(), message);
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
