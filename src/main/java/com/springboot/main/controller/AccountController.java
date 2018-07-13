package com.springboot.main.controller;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.main.customexceptions.ApplicationException;
import com.springboot.main.dto.AccountDTO;
import com.springboot.main.dto.UserDTO;
import com.springboot.main.entity.Account;
import com.springboot.main.enumerations.ResponseStatusCode;
import com.springboot.main.service.AccountService;
import com.springboot.main.util.EntityDTOConverter;
import com.springboot.main.util.StringUtilities;

@RestController
public class AccountController {
    	
    @Autowired
    private AccountService accountService;
    
    private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
    
    @RequestMapping(value = "/delete/{id}", method=RequestMethod.DELETE)
	public String delete(@PathVariable long id) {
	   // logger.trace("/delete called");
    	String st = accountService.delete(id);
		
		return st;
	}
    
    @RequestMapping(value = "/create", method=RequestMethod.POST)
	public String create(@Valid @RequestBody AccountDTO acDTO, BindingResult result) {
	   
    	if(result.hasErrors()) {
    			
    		String fieldName = result.getFieldError().getField();
    		
    		if(fieldName.equals("username"))
    			throw new ApplicationException(ResponseStatusCode.USER_NAME_ERROR);
    			
    		else if(fieldName.equals("fullname"))
    			throw new ApplicationException(ResponseStatusCode.FULL_NAME_ERROR);
    		
    		else if(fieldName.equals("email"))
    			throw new ApplicationException(ResponseStatusCode.EMAIL_ERROR);
    		
    		else if(fieldName.equals("password"))
    			throw new ApplicationException(ResponseStatusCode.PASSWORD_ERROR);
    		
    		else if(fieldName.equals("mobile"))
    			throw new ApplicationException(ResponseStatusCode.CELL_ERROR);	
    	}
    		
    	String st = accountService.create(acDTO);
		
    	return st;
	}
    
    @RequestMapping(value = "/update", method=RequestMethod.PUT)
	public String updateUser(@RequestBody AccountDTO accDto) {		
		
    	String st = accountService.updateUser(accDto);
		
    	return st;
	}
    
    
	@RequestMapping(value = "/get-by-email/{email}", method=RequestMethod.GET)
	public AccountDTO getByEmail(@PathVariable String email)
	{
		Account account = accountService.getByEmail(email);
		
		if(account!=null){
			AccountDTO acDTO = EntityDTOConverter.entityToDTO(account);
			return acDTO;
		}
		
		return new AccountDTO();	
	}
	
	
	@RequestMapping(value = "/get-by-username/{username}", method=RequestMethod.GET)
	public AccountDTO getByUsername(@PathVariable String username){
		
		Account account = accountService.getByUsername(username);
				
		if(account!=null){
			AccountDTO acDTO = EntityDTOConverter.entityToDTO(account);
			return acDTO;
		}
		
		return new AccountDTO();		
	}
	
	
	@RequestMapping(value = "/get-by-mobile/{mobile}", method=RequestMethod.GET)
	public AccountDTO getByMobile(@PathVariable String mobile){
		
		Account account = accountService.getByMobile(mobile);
		
		if(account!=null){
			AccountDTO acDTO = EntityDTOConverter.entityToDTO(account);
			return acDTO;
		}
		
		return new AccountDTO();		
	}
	
	
	@RequestMapping(value = "/get-user", method=RequestMethod.POST)
	public AccountDTO getUser(@Valid @RequestBody UserDTO acDTO, BindingResult result){
		
		if(result.hasErrors()) {
			
    		String fieldName = result.getFieldError().getField();
    		
    		if(fieldName.equals("username"))
    			throw new ApplicationException(ResponseStatusCode.USER_NAME_ERROR);
    		
    		else if(fieldName.equals("password"))
    			throw new ApplicationException(ResponseStatusCode.PASSWORD_ERROR);
    	}
		
		Account account = accountService.getUser(acDTO.getUsername(), acDTO.getPassword());
		
		if(account.getUsername()!=null){
			AccountDTO accountDTO = EntityDTOConverter.entityToDTO(account);
			return accountDTO;
		}
		
		return new AccountDTO();		
	}
}
