package com.okta.developer.theaters;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.data.mongodb.repository.config.EnableReactiveMongoRepositories;

@SpringBootApplication
@EnableEurekaClient
@EnableReactiveMongoRepositories
public class TheatersApplication {

	public static void main(String[] args) {
		SpringApplication.run(TheatersApplication.class, args);
	}

}
