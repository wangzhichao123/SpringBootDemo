package com.wzc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * Hello world!
 *
 */
@SpringBootApplication
@EnableAspectJAutoProxy(exposeProxy = true)
public class SpringBootAOP
{
    public static void main( String[] args) {
        SpringApplication.run(SpringBootAOP.class, args);
    }
}
