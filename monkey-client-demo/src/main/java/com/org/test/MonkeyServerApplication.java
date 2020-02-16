package com.org.test;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {"com.monkey.client", "com.org.test"}
        )
public class MonkeyServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonkeyServerApplication.class, args);
	}

}
