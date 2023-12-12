package com.panoseko.devtrack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@SpringBootApplication
public class DevtrackApplication {

	public static void main(String[] args) {
		SpringApplication.run(DevtrackApplication.class, args);
	}

	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(@NonNull CorsRegistry registry) {
				registry.addMapping("/**")
						.allowedMethods("*")
//						.allowedOrigins("http://localhost:3000")
						.allowedOrigins("https://devtrack.dedyn.io")
						.allowedHeaders("*")
//						.allowedHeaders("Content-Type, Access-Control-Allow-Headers, Access-Control-Expose-Headers, Content-Disposition, Authorization, X-Requested-With, Access-Control-Allow-Origin")
						.exposedHeaders("Content-Disposition")
						.allowCredentials(true)
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedOriginPatterns("*")
						.maxAge(3600L);
			}
		};
	}

}
