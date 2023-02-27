package com.example.jubtibe.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@CrossOrigin
@Configuration
@EnableWebMvc
public class WebConfig implements WebMvcConfigurer {
    // 작성자 : 조영준
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedHeaders("*")
                .exposedHeaders("*")
                .allowCredentials(true)
                .allowedMethods("OPTIONS","GET","POST","PUT","PATCH","DELETE")
                .maxAge(3600)
        ;
    }
}