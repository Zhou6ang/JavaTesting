package com.mycompany.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.mycompany.app.service.UserService;

@Service
public class OtherService {
	
	@Autowired
	@Qualifier("b")
	private UserService userSrv;
	
	public String getUserInfo() {
		return userSrv.getUserInfo()+">>>OtherService.getUserInfo()";
	}
}
