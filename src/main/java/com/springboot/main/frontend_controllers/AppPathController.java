package com.springboot.main.frontend_controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class AppPathController {
	
	@RequestMapping("/")
	   public String index() {
	      return "login";
	   }
	
	@RequestMapping("/register")
	   public String register() {
	      return "register";
	   }
}
