package com.tech.sus_triage_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableRabbit
@EnableCaching
public class SusTriageApiApplication {
	@Bean
	public CacheManager cacheManager() {
		return new ConcurrentMapCacheManager();
	}

	public static void main(String[] args) {
		SpringApplication.run(SusTriageApiApplication.class, args);
	}

}
