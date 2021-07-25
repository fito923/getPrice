package com.java.price.checking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class PriceCheckingApplication {


	public static void main(String[] args) {

		SpringApplication.run(PriceCheckingApplication.class, args);
	}

}
