package com.springboot.main.enumerations;

public enum ResponseStatusCode {
	
	USERNAME_ALREADY_EXIST(101, "Username already exist"),
	EMAIL_ALREADY_EXIST(102, "Email already exist"),
	CELL_ALREADY_EXIST(103,"Cell# already exist"),
	
	FULL_NAME_ERROR(104,"Full name can not be null and contain numbers, Limit: 10 to 50 characters"),
	USER_NAME_ERROR(105,"username can not be null and contain numbers or spaces, Limit: 5 to 20 characters"),
	EMAIL_ERROR(106,"please provide a valid Email"),
	CELL_ERROR(107,"Cell# should be a number, Limit: 11 to 15 numbers"),
	PASSWORD_ERROR(108,"The password policy is incomplete, Limit: 8 to 15 characters"),
	
	USER_NOT_FOUND(109,"no such user found"),
	PASSWORD_NOT_FOUND(110,"user found! but password is incorrect");
	
	private Integer code;
	private String description;
	
	private ResponseStatusCode(Integer code, String description) {
		this.code = code;
		this.description = description;
	}
	
	public Integer getCode() {
		return code;
	}
	
	public String getDescription() {
		return description;
	}
	
}
