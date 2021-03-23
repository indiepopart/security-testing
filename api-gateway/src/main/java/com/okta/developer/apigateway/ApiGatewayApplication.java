package com.okta.developer.apigateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.security.oauth2.gateway.TokenRelayGatewayFilterFactory;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableEurekaClient
public class ApiGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

	@Bean
	public RouteLocator routeLocator(RouteLocatorBuilder builder, TokenRelayGatewayFilterFactory filterFactory) {
		return builder.routes()
				.route("listing", r -> r.path("/listing/**")
						.filters(f -> f.stripPrefix(1).prefixPath("/airbnb")
								.filter(filterFactory.apply()))
						.uri("lb://airbnb"))
                .route("theater", r -> r.path("/theater/**")
                        .filters(f -> f.filter(filterFactory.apply()))
                        .uri("lb://theater"))
				.build();
	}
}
