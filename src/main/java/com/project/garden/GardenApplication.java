package com.project.garden;

import com.project.garden.security.user.service.UserService;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GardenApplication {

	public static void main(String[] args) {
		SpringApplication.run(GardenApplication.class, args);
	}

	@Bean
	public ApplicationRunner init(UserService userService) {
		return args -> {
			userService.populate();
		};
	}

}
