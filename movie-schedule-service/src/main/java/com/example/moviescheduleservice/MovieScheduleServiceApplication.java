package com.example.moviescheduleservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
public class MovieScheduleServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieScheduleServiceApplication.class, args);
	}




}
