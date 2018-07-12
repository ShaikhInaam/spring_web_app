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

import com.springboot.main.controller.AccountController;
import com.springboot.main.dto.AccountDTO;
import com.springboot.main.hashing.MD5Coverter;
import com.springboot.main.util.StringUtilities;


@Controller
public class RegistrationController {
	
	@Autowired
	AccountController accountController;

	@PostMapping("/registration")
    public String registerSubmit(@Valid AccountDTO accountDTO, BindingResult result, ModelMap model){
	
		AccountDTO acDTO;
		
		if(result.hasErrors()) {
			model.addAttribute("error",result.getFieldError().getDefaultMessage());
			return "register";
		}
		
		acDTO=accountController.getByUsername(accountDTO.getUsername());
		
		if(!StringUtilities.isEmpty(acDTO.getFullname())){
			model.addAttribute("error","username is already taken");
			return "register";
		}
		
		acDTO=accountController.getByEmail(accountDTO.getEmail());
		
		if(!StringUtilities.isEmpty(acDTO.getFullname())){
			model.addAttribute("error","email is already taken");
			return "register";
		}
		
		acDTO=accountController.getByMobile(accountDTO.getMobile());
		
		if(!StringUtilities.isEmpty(acDTO.getFullname())){
			model.addAttribute("error","Cell# is already taken");
			return "register";
		}
		
		accountDTO.setPassword(MD5Coverter.convert(accountDTO.getPassword()));
		accountController.create(accountDTO,result);
		model.addAttribute("success","successfull! now you can login");
        
		return "login";
    }
	
	@GetMapping("/registration")
	public String returnRegistration(){
		return "register";
	}
}