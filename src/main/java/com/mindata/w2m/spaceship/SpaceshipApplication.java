package com.mindata.w2m.spaceship;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@EnableAspectJAutoProxy
@SpringBootApplication
public class SpaceshipApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpaceshipApplication.class, args);
	}

}
