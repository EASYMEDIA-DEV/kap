package com.kap.mngwserc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication(scanBasePackages = "com.kap")
public class MngwsercApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MngwsercApplication.class, args);
	}

}
