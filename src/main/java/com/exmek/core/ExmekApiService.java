package com.exmek.core;

import java.io.File;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.exmek.core.resource.UserResourceManager;


//@EnableCaching
@SpringBootApplication(
		scanBasePackages = "com.exmek.core"
)
@EntityScan("com.exmek.core.persistence.entity")
@EnableJpaRepositories("com.exmek.core.persistence.repository")
@EnableScheduling
public class ExmekApiService {

	public static void main(String[] args) {
		if (System.getProperty(UserResourceManager.SYS_PROP_NAME_USER_RESOURCES_LOCATION) == null) {
			String userResourcesLocation = System.getProperty("user.dir") + File.separator + "user-resources";
			if (!userResourcesLocation.startsWith("/")) {
				userResourcesLocation = "/" + userResourcesLocation;
			}
			System.setProperty(UserResourceManager.SYS_PROP_NAME_USER_RESOURCES_LOCATION, userResourcesLocation);
		}
		SpringApplication.run(ExmekApiService.class, args);
	}

}
