package com.example.demopollingdb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DemoPollingDbApplication {
    public static void main(String[] args) {
        SpringApplication.run(DemoPollingDbApplication.class, args);
    }
}
