package com.example.demo.controllers;

import org.apache.logging.log4j.message.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.demo.entities.MessageTest;







@Controller
public class testController {


    @MessageMapping("/chat.send")
    @SendTo("/topic/messages")
    public MessageTest deliverMessage(MessageTest message) {
        return new MessageTest(message.getText());

    }
}
