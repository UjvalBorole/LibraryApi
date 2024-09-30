package com.libraryapi.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/api/**") // Specify the endpoint(s) you want to apply CORS to
                        .allowedOrigins("http://localhost:5173") // Allow this origin (React app)
                        .allowedMethods("GET", "POST", "PUT", "DELETE") // Allow HTTP methods
                        .allowedHeaders("*") // Allow any header
                        .allowCredentials(true) // Allow credentials (like cookies)
                        .maxAge(3600); // Cache preflight responses for 1 hour
            }
        };
    }
}

