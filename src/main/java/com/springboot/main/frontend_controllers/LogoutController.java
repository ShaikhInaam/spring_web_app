package com.springboot.main.frontend_controllers;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.springboot.main.controller.AccountController;
import com.springboot.main.dto.AccountDTO;
import com.springboot.main.util.LogedUsersData;

@Controller
public class LogoutController {

	@Autowired
	AccountController accountController;

	@PostMapping("/logout")
    public String logoutSubmit( Model model, HttpSession session) {
		
		LogedUsersData.mp.remove(session.getAttribute("id"));
		session.invalidate();
		
        return "login";		
    }
	
	@GetMapping("/logout")
    public String logout(Model model) {
		
        return "login";
    }
}
