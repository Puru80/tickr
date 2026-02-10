package com.example.tickr.tickr.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Apply to all endpoints
            .allowedOrigins(frontendUrl) // Specify allowed frontend origins
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Specify allowed methods
            .allowedHeaders("*") // Allow all headers
            .allowCredentials(true) // Allow credentials (cookies, auth tokens)
            .maxAge(3600); // Cache preflight response for 1 hour
    }
}