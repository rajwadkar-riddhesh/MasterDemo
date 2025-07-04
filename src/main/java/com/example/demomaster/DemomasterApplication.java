package com.example.demomaster;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemomasterApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemomasterApplication.class, args);
	}

}
