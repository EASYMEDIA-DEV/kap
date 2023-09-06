package com.kap.front;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.kap")
public class ClientApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(ClientApplication.class, args);
	}

}
