package com.exmek.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;


//@EnableCaching
@SpringBootApplication(
		scanBasePackages = "com.exmek.core"
)
@EntityScan("com.exmek.core.persistence.entity")
@EnableJpaRepositories("com.exmek.core.persistence.repository")
@EnableScheduling
public class ExmekApiService {

	public static void main(String[] args) {
		SpringApplication.run(ExmekApiService.class, args);
	}

}
