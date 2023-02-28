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
        registry.addMapping("/**") // url(도메인 뒤에 붙는)
//                .allowedOriginPatterns("*") // 도메인
                .allowedOrigins("http://localhost:3000","http://s3-react-4.s3-website.ap-northeast-2.amazonaws.com","http://localhost:8080")
                .allowedHeaders("*")
                .exposedHeaders("Authorization")
                .allowCredentials(true)
                .allowedMethods("OPTIONS","GET","POST","PUT","PATCH","DELETE")
                .maxAge(3600)
        ;
    }
}