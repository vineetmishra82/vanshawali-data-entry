package com.pts.unige.main;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
@ComponentScan(basePackages = {"com.pts.unige.Controller","com.pts.unige.Service"})
@EnableMongoRepositories("com.pts.unige.Repositories")
public class UnigeRestApIsApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnigeRestApIsApplication.class, args);
	}
	
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				  registry.addMapping("/**")
	                .allowedMethods("GET", "POST","DELETE");
			}
		};
	}

}
