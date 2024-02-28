package com.wzc.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Retention(RetentionPolicy.RUNTIME)                 //运行时生效
@Target(ElementType.METHOD)                         //作用在方法上
public @interface RequestLock {
    /**
     * key的前缀
     */
    String prefixKey() default "";

    /**
     * 过期时间
     */
    int expire();

    /**
     * 时间单位，默认秒
     */
    TimeUnit timeUnit() default TimeUnit.SECONDS;

    /**
     * 前缀分隔符，默认"-"
     */
    String delimiter() default "-";

}
