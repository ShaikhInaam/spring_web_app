package com.springboot.main.util;

import org.springframework.beans.BeanUtils;

import com.springboot.main.dto.AccountDTO;
import com.springboot.main.entity.Account;

public class EntityDTOConverter {

	public static AccountDTO entityToDTO(Account ac){
		AccountDTO acDto=new AccountDTO();
		BeanUtils.copyProperties(ac, acDto);
		
		return acDto;
	}
	
	public static Account dtoToEntity(AccountDTO acDto){
		Account ac=new Account();
		BeanUtils.copyProperties(acDto, ac);		
		
		return ac;
	}
}