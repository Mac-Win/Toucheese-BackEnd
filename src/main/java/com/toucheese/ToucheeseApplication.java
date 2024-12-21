package com.toucheese;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ToucheeseApplication {

	public static void main(String[] args) {
		SpringApplication.run(ToucheeseApplication.class, args);
	}

}
