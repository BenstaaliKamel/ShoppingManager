package com.cash_manager.api.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // Allow CORS for all endpoints
            .allowedOrigins("http://34.125.87.139","http://localhost:4001","http://localhost") // Allow requests from this origin
            .allowedMethods("*") // Allow all HTTP methods
            .allowedHeaders("*") // Allow all headers
            .allowCredentials(true); // Allow sending cookies
    }
}