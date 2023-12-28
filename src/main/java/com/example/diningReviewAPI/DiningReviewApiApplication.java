package com.example.diningReviewAPI;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.example.diningReviewAPI.repository")
@EntityScan(basePackages = "com.example.diningReviewAPI.entities")
public class DiningReviewApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(DiningReviewApiApplication.class, args);
	}
}
