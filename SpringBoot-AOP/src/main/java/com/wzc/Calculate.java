package com.wzc;

import org.springframework.aop.framework.AopContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
public class Calculate {
    @Resource
    private Calculate calculate;
    public int add(int numA, int numB)
    {
        System.out.println("执行目标方法:add");
        try {

        }catch (ArithmeticException e){
            e.printStackTrace();
        }
        return numA + numB;
    }
    public int sub(int numA, int numB)
    {
        System.out.println("执行目标方法:reduce");
        return numA - numB;
    }
    public int div(int numA, int numB){
        System.out.println("执行目标方法:div");
        return numA / numB;
    }
    public int multi(int numA, int numB)
    {
        System.out.println("执行目标方法:multi");
        return numA * numB;
    }
    public int mod(int numA, int numB)
    {
        System.out.println("执行目标方法:mod");
        // int retVal=((Calculate) AopContext.currentProxy()).add(numA, numB);
        int retVal= calculate.add(numA,numB);
        return retVal % numA;
    }
}
