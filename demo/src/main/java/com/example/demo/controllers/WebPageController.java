package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;

import com.example.demo.service.ChatService;
import com.example.demo.entities.Chat;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class WebPageController {

	private final ChatService chatService;

	@GetMapping({"/", "/sign-in"})
	public String signIn() {
		return "sign-in";
	}

	@GetMapping("/sign-up")
	public String signUp() {
		return "sign-up";
	}

	@GetMapping("/chats")
	public String chats(Model model, Authentication auth) {
		var chats = chatService.getChats(auth);
		for(int i = 0;i < chats.size();i++){
			System.out.println(chats.get(i).getName());
		}
		model.addAttribute("chats", chats);
		return "chats";
	}

	public record ChatPreview(String name, String lastSender, String lastMessage, String time, boolean active) {
	}

	public record ChatMessage(String sender, String body, String time, boolean incoming) {
	}
}