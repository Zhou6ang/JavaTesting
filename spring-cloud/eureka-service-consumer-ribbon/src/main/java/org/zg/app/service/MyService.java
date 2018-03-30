package org.zg.app.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class MyService {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public String requestService() {
		return restTemplate.getForObject("http://eureka-service-provider/hi", String.class);
	}

}
