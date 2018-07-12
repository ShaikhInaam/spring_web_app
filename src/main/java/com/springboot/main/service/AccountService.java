package com.springboot.main.service;


import org.springframework.validation.BindingResult;

import com.springboot.main.dto.AccountDTO;
import com.springboot.main.entity.Account;


public interface AccountService {
   
    public String delete(Long id);
    public String create(AccountDTO acDto);
	public Account getByEmail(String email);
	public Account getByUsername(String username);
	public Account getByMobile( String mobile);
	public String updateUser(AccountDTO ac);
	public Account getUser(String username, String password);
}
