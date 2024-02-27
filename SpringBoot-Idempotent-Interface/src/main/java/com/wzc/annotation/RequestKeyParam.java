package com.wzc.annotation;

import java.lang.annotation.*;

/**
 * @description 加上这个注解可以将参数设置为key
 */
@Target({ElementType.METHOD, ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented         // 提供参考文档
public @interface RequestKeyParam {

}
