package com.example.demo.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.demo.service.ChatService;

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
		public String chats(Model model) {
		model.addAttribute( "chats",chatService.getChats());
		return "chats";
	}

	public record ChatPreview(String name, String lastSender, String lastMessage, String time, boolean active) {
	}

	public record ChatMessage(String sender, String body, String time, boolean incoming) {
	}
}