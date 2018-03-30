package org.zg.app.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.zg.app.service.MyService;

@RestController
public class MyController {
	
	@Autowired
	private MyService service;
	
	@RequestMapping("/ribbon")
	public String sendRequest() {
		return service.requestService();
	}
}
