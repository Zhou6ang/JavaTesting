package com.mycompany.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.mycompany.app.model.User;
import com.mycompany.app.model.UserInfo;
import com.mycompany.app.service.UserService;

@Configuration
public class ConfigurationAnn {
	
	@Autowired
	@Qualifier("b")
	private UserService userSrv;
	
	@Bean
	public User getUser1() {
		User user = new User();
		user.setName("test1-"+user);
		user.setAge(10);
		return user;
	}
	
	@Bean
	public UserInfo getUser2() {
		User u = getUser1();
		UserInfo user = new UserInfo();
		user.setName(u.getName());
		user.setAge(u.getAge());
		user.setOther(userSrv.getUserInfo()+">>>user1111111111");
		return user;
	}
	
}
