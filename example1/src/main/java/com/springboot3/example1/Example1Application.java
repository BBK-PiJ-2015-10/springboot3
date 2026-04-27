package com.springboot3.example1;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


//TODO:
// Remove in-memory
// Re-enable security

@SpringBootApplication
public class Example1Application {

    static void main(String[] args) {
        SpringApplication.run(Example1Application.class, args);
    }

}
