package com.pts.unige.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication
@ComponentScan(basePackages = {"com.pts.unige.Controller","com.pts.unige.Service"})
@EnableMongoRepositories("com.pts.unige.Repositories")
public class UnigeRestApIsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnigeRestApIsApplication.class, args);
	}
	
	
}
