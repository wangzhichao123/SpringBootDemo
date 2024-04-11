package com.wzc;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SpringBootIdempotentInterfaceApplication
{
    public static void main(String[] args) {
        SpringApplication.run(SpringBootIdempotentInterfaceApplication.class, args);
    }
}
