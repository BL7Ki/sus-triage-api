package com.tech.sus_triage_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableRabbit
@EnableCaching
public class SusTriageApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SusTriageApiApplication.class, args);
	}

}
