package com.mycompany.app.service.impl;

import org.springframework.stereotype.Service;

import com.mycompany.app.service.UserService;

@Service("b")
public class UserServiceImpl2 implements UserService {

	@Override
	public String getUserName() {
		return "This implemented by hahahha.";
	}

	@Override
	public String getUserInfo() {
		return "hahahahah";
	}

}
