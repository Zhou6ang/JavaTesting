package org.zg.app;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

	@Value("${server.port}")
    private String port;
	
    @RequestMapping("/hi")
    public String home() {
        return "hello world from spring cloud, I am from port:" +port;
    }
}
