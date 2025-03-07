package com.example.samuraitravel.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class AuthController {    /* Auth=認証 */
	@GetMapping("/login")
	public String login() {
		return "auth/login";
	}

}
