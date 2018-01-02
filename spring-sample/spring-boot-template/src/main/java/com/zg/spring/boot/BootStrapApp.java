package com.zg.spring.boot;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.support.SpringBootServletInitializer;

/**
 * Hello world!
 *
 */
@SpringBootApplication
public class BootStrapApp extends SpringBootServletInitializer {
	private static final Logger log = LogManager.getLogger(BootStrapApp.class);

	public static void main(String[] args) {
		log.info("Starting BootStrapApp ...");
		SpringApplication.run(BootStrapApp.class, args);
		log.info("Starting BootStrapApp Done.");
	}
}
