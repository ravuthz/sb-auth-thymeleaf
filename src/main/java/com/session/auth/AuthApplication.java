package com.session.auth;

import nz.net.ultraq.thymeleaf.layoutdialect.LayoutDialect;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class AuthApplication {

    @Bean
    public LayoutDialect layoutDialect() {
        return new LayoutDialect();
    }

    public static void main(String[] args) {
        SpringApplication.run(AuthApplication.class, args);
    }

}

// 10 months, 3 phases
// Requirement 3
// Develop 3
// Test 1

// Contract
// Free Source, Free Deploy, 3 months maintained and fixed bugs


