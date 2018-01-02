package com.mycompany.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.mycompany.app.model.User2;
import com.mycompany.app.service.UserService;

@Component
public class BeanAnnStandalone {
	
	@Autowired
	@Qualifier("b")
	private UserService userSrv;
	
	@Bean
	public User2 getMap() {
		User2 user2 = new User2();
		user2.setName(userSrv.getUserInfo());
		return user2;
	}
	
}
