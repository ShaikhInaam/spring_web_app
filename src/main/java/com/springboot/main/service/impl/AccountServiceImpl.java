package com.springboot.main.service.impl;

import com.springboot.main.controller.AccountController;
import com.springboot.main.customexceptions.ApplicationException;
import com.springboot.main.dto.AccountDTO;
import com.springboot.main.entity.Account;
import com.springboot.main.enumerations.ResponseStatusCode;
import com.springboot.main.hashing.BCryptConverter;
import com.springboot.main.repository.AccountRepository;
import com.springboot.main.service.AccountService;
import com.springboot.main.util.EntityDTOConverter;
import com.springboot.main.util.LogRecorder;
import com.springboot.main.util.StringUtilities;
import java.util.logging.FileHandler;
import java.util.logging.SimpleFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class AccountServiceImpl implements AccountService {
    
    static final Logger logger = LoggerFactory.getLogger(AccountServiceImpl.class);
   
    
    
    @Autowired
	AccountController accountController;
    
    @Autowired
    private AccountRepository accountRepository;
    

	@Override
	public String delete(Long id) {
		
		accountRepository.deleteById(id);
		
		return "user deleted successfully!";
	}
	
	@Override
	public Account getByEmail(String email){
		
		Account account = accountRepository.findByEmail(email);
			
		return account;		
	}
		
	@Override
	public Account getByUsername(String username){
		
		Account account = accountRepository.findByUsername(username);
		
		return account;		
	}
		
	@Override
	public Account getByMobile( String mobile){
		
		Account account = accountRepository.findByMobile(mobile);
			
		return account;		
	}
		
	@Override
	public String create(AccountDTO acDTO) {
		logger.info("AccountServiceImpl.java : create() called: info");
		logger.trace("AccountServiceImpl.java : create() called: trace");
		
		AccountDTO accountDTO;
		
		//logger.info("AccountServiceImpl.java : checking for username existance");
		accountDTO=accountController.getByUsername(acDTO.getUsername());
		
		if(!StringUtilities.isEmpty(accountDTO.getFullname())) {
			//logger.info("AccountServiceImpl.java : throwing username already exists");
			throw new ApplicationException(ResponseStatusCode.USERNAME_ALREADY_EXIST);
		}
		
		//logger.info("AccountServiceImpl.java : checking for cell# existance");
		accountDTO=accountController.getByMobile(acDTO.getMobile());
		if(!StringUtilities.isEmpty(accountDTO.getFullname())){
		//	logger.info("AccountServiceImpl.java : throwing cell# already exists");
			throw new ApplicationException(ResponseStatusCode.CELL_ALREADY_EXIST);
		}
		
		//logger.info("AccountServiceImpl.java : checking for email existance");
		accountDTO=accountController.getByEmail(acDTO.getEmail());
		if(!StringUtilities.isEmpty(accountDTO.getFullname())){
		//	logger.info("AccountServiceImpl.java : throwing email already exists");
			throw new ApplicationException(ResponseStatusCode.EMAIL_ALREADY_EXIST);
		}
		
		acDTO.setPassword(BCryptConverter.convert(acDTO.getPassword()));
		Account ac = EntityDTOConverter.dtoToEntity(acDTO);
		accountRepository.saveAndFlush(ac);
		
		//logger.info("AccountServiceImpl.java : create(): user created successfully");
		return "user created successfully!";
	}
		
	@Override
	public String updateUser(AccountDTO ac) {
		
		Account account = accountRepository.findById(ac.getId());
		
		account.setUsername(ac.getUsername());
		account.setEmail(ac.getEmail());
		account.setFullname(ac.getFullname());
		account.setMobile(ac.getMobile());
		account.setPassword(ac.getPassword());
		
		accountRepository.saveAndFlush(account);
			
		return "user updated successfully!";
			
		}

	@Override
	public Account getUser(String username, String password) {
		
		//logger.info("AccountServiceImpl.java : getUser() is executing ");
		
		Account account = accountRepository.findByUsername(username);
		if(account!=null) {
			if(BCryptConverter.compare(password, account.getPassword()))
			{
				//logger.info("AccountServiceImpl.java : user login success");
				return account;
			}
			else
				throw new ApplicationException(ResponseStatusCode.PASSWORD_NOT_FOUND);
		}
		else
			throw new ApplicationException(ResponseStatusCode.USER_NOT_FOUND);
			
	}
}