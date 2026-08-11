package com.example.demo.controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping
public class WebPageController {

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
		model.addAttribute("chats", List.of(
			new ChatPreview("Product Team", "Nadia", "Shipping the onboarding redesign today.", "2m ago", true),
			new ChatPreview("General", "You", "I posted the new design mockups.", "12m ago", false),
			new ChatPreview("Gaming Squad", "Omar", "Queue up at 9 PM.", "1h ago", false)
		));
		model.addAttribute("messages", List.of(
			new ChatMessage("Nadia", "Let’s keep the new sidebar compact on mobile.", "8:42 AM", true),
			new ChatMessage("You", "Agreed. I’ll tighten the spacing and keep the actions visible.", "8:44 AM", false),
			new ChatMessage("Nadia", "Perfect. The app should feel fast and readable.", "8:46 AM", true)
		));
		return "chats";
	}

	public record ChatPreview(String name, String lastSender, String lastMessage, String time, boolean active) {
	}

	public record ChatMessage(String sender, String body, String time, boolean incoming) {
	}
}