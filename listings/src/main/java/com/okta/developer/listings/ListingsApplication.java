package com.okta.developer.listings;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;


@SpringBootApplication
@EnableEurekaClient
public class ListingsApplication {

	public static void main(String[] args) {
		SpringApplication.run(ListingsApplication.class, args);
	}

}
