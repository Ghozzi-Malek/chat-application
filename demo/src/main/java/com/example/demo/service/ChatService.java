package com.example.demo.service;

import java.util.List;

import org.apache.tomcat.util.net.openssl.ciphers.Authentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Chat;
import com.example.demo.repository.ChatsRepository;
import com.example.demo.repository.ChatsRepositoryImp;

@Service
public class ChatService {
    private final ChatsRepositoryImp chatsRepository;

    public ChatService(ChatsRepositoryImp chatsRepository){
        this.chatsRepository = chatsRepository;
    }

    public List<String> getChats(Authentication auth){
        String userId = auth.getName();
        return chatsRepository.getChats(userId);
    }
}
