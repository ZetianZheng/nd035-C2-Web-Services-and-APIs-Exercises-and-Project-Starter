package com.zane.careurekaserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

/**
 * @EnableEurekaServer noticed this is a eureka server
 */
@SpringBootApplication
@EnableEurekaServer
public class CarEurekaServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(CarEurekaServerApplication.class, args);
	}

}
