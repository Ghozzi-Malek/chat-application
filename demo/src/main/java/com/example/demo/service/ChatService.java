package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.entities.Chat;
import com.example.demo.repository.ChatsRepository;

@Service
public class ChatService {
    private final ChatsRepository chatsRepository;

    public ChatService(ChatsRepository chatsRepository){
        this.chatsRepository = chatsRepository;
    }

    public List<Chat> getChats(){
        
    }
}
