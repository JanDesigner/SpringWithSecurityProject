package com.springboot.service;

import com.springboot.model.User;

public interface UserService {
		
	User findUserByEmail(String email);
	
	public void saveUser(User user);
	
	
}
