package com.example.jubtibe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class JuBtiBeApplication {

    public static void main(String[] args) {
        SpringApplication.run(JuBtiBeApplication.class, args);
    }

}
