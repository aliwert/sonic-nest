package com.aliwert.starter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.aliwert"})
@ComponentScan(basePackages = {"com.aliwert"})
@EnableJpaRepositories(basePackages = {"com.aliwert"})
@SpringBootApplication
public class SpotifyApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpotifyApiApplication.class, args);
	}

}
