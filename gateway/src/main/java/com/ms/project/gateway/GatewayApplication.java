package com.ms.project.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableDiscoveryClient
public class GatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(GatewayApplication.class, args);
	}

	@Bean
	public RouteLocator router(RouteLocatorBuilder builder) {
		return builder
				.routes()
				.route(r -> r
						.path("/products/**")
						.uri("lb://ms-product"))
				.route(r -> r
						.path("/orders/**")
						.uri("lb://ms-order"))
				.build();
	}
}
