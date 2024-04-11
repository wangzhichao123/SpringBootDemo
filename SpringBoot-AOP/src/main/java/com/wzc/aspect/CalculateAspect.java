package com.wzc.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import java.util.Arrays;

@Aspect
@Configuration
@Order(0)
public class CalculateAspect{
    /**
     * *: 表示匹配任意的返回类型。
     * com.wzc.Calculate: 表示匹配 com.wzc.Calculate 类。
     * *: 表示匹配所有方法
     * (..): 表示任意参数
     */
    @Pointcut("execution(* com.wzc.Calculate.*(..))")
    public void pointcut(){}

    @Before(value = "pointcut()")
    public void methodBefore(JoinPoint joinPoint) throws Throwable{
        String methodName = joinPoint.getSignature().getName();
        System.out.println("执行目标方法【"+methodName+"】的<前置通知>,入参"+ Arrays.asList(joinPoint.getArgs()));
    }

    @After(value = "pointcut()")
    public void methodAfter(JoinPoint joinPoint) throws Throwable{
        String methodName = joinPoint.getSignature().getName();
        System.out.println("执行目标方法【"+methodName+"】的<后置通知>,入参"+ Arrays.asList(joinPoint.getArgs()));
    }
}
