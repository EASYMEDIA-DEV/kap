package com.kap.mngwserc;

import com.kap.mngwserc.config.SessionListener;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.servlet.http.HttpSessionListener;

@SpringBootApplication(scanBasePackages = "com.kap")
public class MngwsercApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(MngwsercApplication.class, args);
	}

	@Bean
	public HttpSessionListener httpSessionListener(){
		return new SessionListener();
	}
}
