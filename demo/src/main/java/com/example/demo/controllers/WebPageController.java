package com.example.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.demo.service.ChatService;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;

@Controller
@RequestMapping
@RequiredArgsConstructor
public class WebPageController {

	private final ChatService chatService;
	private final UserService userService;

	@GetMapping({"/", "/sign-in"})
	public String signIn() {
		return "sign-in";
	}

	@GetMapping("/sign-up")
	public String signUp() {
		return "sign-up";
	}

	@PostMapping("/sign-up")
	public String createAccount(
			@RequestParam String name,
			@RequestParam String email,
			@RequestParam String password,
			Model model) {
		try {
			userService.register(name, email, password);
			return "redirect:/sign-in?registered=true";
		} catch (IllegalArgumentException exception) {
			model.addAttribute("signupError", exception.getMessage());
			return "sign-up";
		}
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