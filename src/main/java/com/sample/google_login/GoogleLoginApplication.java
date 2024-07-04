package com.sample.google_login;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(com.sample.google_login.config.GoogleProperties.class)
public class GoogleLoginApplication {

	public static void main(String[] args) {
		SpringApplication.run(GoogleLoginApplication.class, args);
	}

}
