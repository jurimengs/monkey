package com.org.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({"com.org.test", "com.monkey.client"})
public class MonkeyClientApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonkeyClientApplication.class, args);
	}

}
