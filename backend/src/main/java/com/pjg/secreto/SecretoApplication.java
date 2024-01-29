package com.pjg.secreto;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


@SpringBootApplication
public class SecretoApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecretoApplication.class, args);
	}

}
