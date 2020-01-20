package org.monkey.db;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(
        scanBasePackages = {"org.monkey.db"}
        )
public class MonkeyApplication {
	public static void main(String[] args) {
		SpringApplication.run(MonkeyApplication.class, args);
	}

}
