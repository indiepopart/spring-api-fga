package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableTransactionManagement
public class FgaTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(FgaTestApplication.class, args);
	}

}
