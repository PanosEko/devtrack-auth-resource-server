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
//						.allowedOrigins("http://localhost:8080","http://localhost:3000")
						.allowedOrigins("https://app.getpostman.com", "https://devtrack-frontend.vercel.app",
								"api.devtrack.dedyn.io", "https://devtrack.dedyn.io", "https://api.devtrack.dedyn.io",
								"http://api.devtrack.dedyn.io","http://devtrack.dedyn.io")
						.allowedHeaders("*")
						.allowCredentials(true)
						.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
						.allowedOriginPatterns("*")
						.maxAge(3600L);
			}
		};
	}

}
