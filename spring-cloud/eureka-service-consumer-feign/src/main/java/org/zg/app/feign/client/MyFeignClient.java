package org.zg.app.feign.client;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient("eureka-service-provider")
public interface MyFeignClient {

	@RequestMapping(value="/hi",method=RequestMethod.GET)
	public String invokeHiFromServiceProvider();
}
