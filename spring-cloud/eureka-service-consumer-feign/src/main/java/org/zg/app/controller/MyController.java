package org.zg.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zg.app.feign.client.MyFeignClient;

@RestController
public class MyController {
	
	@Autowired
	private MyFeignClient feign;
	
	@RequestMapping("/feign")
	public String sendRequest() {
		return feign.invokeHiFromServiceProvider();
	}
}
