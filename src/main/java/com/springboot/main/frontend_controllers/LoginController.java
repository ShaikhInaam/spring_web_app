package com.springboot.main.frontend_controllers;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.springboot.main.controller.AccountController;
import com.springboot.main.dto.AccountDTO;
import com.springboot.main.dto.UserDTO;
import com.springboot.main.entity.Account;
import com.springboot.main.hashing.MD5Coverter;
import com.springboot.main.util.LogedUsersData;
import com.springboot.main.util.StringUtilities;

@Controller
public class LoginController {

	@Autowired
	AccountController accountController;
	
	@PostMapping("/login")
    public String loginSubmit(@Valid UserDTO userDTO,BindingResult result, ModelMap model, HttpSession session) {
		
		AccountDTO accountDTO;
		
		if(result.hasErrors()) {
			model.addAttribute("error",result.getFieldError().getDefaultMessage());
			return "login"; 
		}
		
		userDTO.setPassword(MD5Coverter.convert(userDTO.getPassword()));
		accountDTO = accountController.getUser(userDTO,result);
		
		if(!StringUtilities.isEmpty(accountDTO.getFullname())){
			
			 session.setAttribute("id", accountDTO.getId());
             session.setAttribute("fullName", accountDTO.getFullname());
             session.setAttribute("username", accountDTO.getUsername());
             session.setAttribute("email", accountDTO.getEmail());
             session.setAttribute("mobile", accountDTO.getMobile());
             session.setAttribute("password", accountDTO.getPassword());
             LogedUsersData.mp.put(accountDTO.getId(), accountDTO.getFullname());
             
			return "hello";
		}	
		
		else {
			model.addAttribute("error","no such user found!");
			return "login";
		}
    }
	
	@GetMapping("/login")
	public String returnLogin(ModelMap model, HttpSession session)
	{
		if(session.getAttribute("fullname")!=null) 
			return "home";
		
		return "login";
	}
		
}
