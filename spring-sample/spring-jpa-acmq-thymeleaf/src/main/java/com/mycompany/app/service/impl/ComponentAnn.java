package com.mycompany.app.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import com.mycompany.app.model.User1;
import com.mycompany.app.model.UserInfo1;
import com.mycompany.app.service.UserService;

@Component
public class ComponentAnn {
	
	@Autowired
	@Qualifier("b")
	private UserService userSrv;
	
	@Bean
	public User1 getUser3() {
		User1 user = new User1();
		user.setName("test1-"+user);
		user.setAge(10);
		return user;
	}
	
	@Bean
	public UserInfo1 getUser4() {
		User1 u = getUser3();
		UserInfo1 user = new UserInfo1();
		user.setName(u.getName());
		user.setAge(u.getAge());
		user.setOther(userSrv.getUserInfo()+">>>user1111111111");
		return user;
	}
	
	public String getInstance() {
		return "here is instance of ComponentAnn, try to invoke getUser4()-->>>> result:"+getUser4().getOther();
	}
}
