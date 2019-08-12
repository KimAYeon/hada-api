package com.hada.api;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.Configuration;

@SpringBootApplication
@Configuration
public class HadaApplication {
	
	public static final String APPLICATION_LOCATIONS = "spring.config.location="
            + "classpath:application.properties,"
            + "classpath:aws.yml";

	public static void main(String[] args) {
		new SpringApplicationBuilder(HadaApplication.class)
			.properties(APPLICATION_LOCATIONS)
			.run(args);
	}
	
}
