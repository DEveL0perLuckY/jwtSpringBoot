package com.example.jwtauth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JwtauthApplication {

    public static void main(final String[] args) {
        SpringApplication.run(JwtauthApplication.class, args);
        System.out.println("Server Started");
    }

}
