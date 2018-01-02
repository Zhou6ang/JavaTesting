package com.mycompany.app.service.impl;

import org.springframework.stereotype.Service;

import com.mycompany.app.service.UserService;

@Service("a")
public class UserServiceImpl implements UserService {

	@Override
	public String getUserName() {
		return "This implemented by @Service.";
	}

	@Override
	public String getUserInfo() {
		return "UserServiceImpl.getUserInfo()";
	}

}
