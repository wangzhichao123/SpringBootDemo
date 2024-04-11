package com.wzc;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.annotation.Resource;

@SpringBootTest()
public class SpringBootAOPTest {
    @Resource
    private ApplicationContext applicationContext;
    @Test
    public void name() {
        Calculate calculate = (Calculate) applicationContext.getBean("calculate");
        System.out.println(calculate.getClass());
        int retval = calculate.mod(4, 5);

    }
}
