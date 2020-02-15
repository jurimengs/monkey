package com.monkey.server;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {"com.monkey.server"}
        )
public class MonkeyServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonkeyServerApplication.class, args);
	}

}
