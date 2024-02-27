package com.wzc.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

import com.wzc.annotation.RequestKeyParam;
import com.wzc.annotation.RequestLock;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;
public class RequestKeyGenerator {
    /**
     * 需求1:public void exampleMethod(@RequestKeyParam String name, @RequestKeyParam String phone)
     * 需求2: @TableId、
     *       @RequestKeyParam
     *       private String name;
     * @param joinPoint 切入点
     * @return LockKey 唯一key
     */
    public static String getLockKey(ProceedingJoinPoint joinPoint) {
        // 1、获取连接点的方法签名对象
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        // 2、获取方法对象
        Method method = methodSignature.getMethod();
        // 2、获取方法对象上的注解对象
        RequestLock requestLock = method.getAnnotation(RequestLock.class);
        // 3、获取实际入参
        final Object[] args = joinPoint.getArgs();
        // 4、获取方法参数
        final Parameter[] parameters = method.getParameters();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parameters.length; i++) {
            final RequestKeyParam keyParam = parameters[i].getAnnotation(RequestKeyParam.class);
            // 5、如果属性不是@RequestKeyParam注解，则不处理
            if (keyParam == null) {
                continue;
            }
            // 6、如果属性是RequestKeyParam注解，则拼接连接符"-" + RequestKeyParam
            sb.append(requestLock.delimiter()).append(args[i]);
        }
        // 7、如果方法上没有加@RequestKeyParam注解（此时会在自定义类的属性字段上）
        if (StringUtils.isEmpty(sb.toString())) {
            // 8、获取方法上的多个注解（为什么是两层数组：因为第二层数组是只有一个元素的数组）
            final Annotation[][] parameterAnnotations = method.getParameterAnnotations();
            for (int i = 0; i < parameterAnnotations.length; i++) {
                // ************ //
                final Object object = args[i];
                // 9、获取注解类中所有的属性字段
                final Field[] fields = object.getClass().getDeclaredFields();
                for (Field field : fields) {
                    // 10、判断字段上是否有@RequestKeyParam注解
                    final RequestKeyParam annotation = field.getAnnotation(RequestKeyParam.class);
                    if (annotation == null) {
                        continue;
                    }
                    // 11、如果有，设置Accessible为true（为true时可以使用反射访问私有变量，否则不能访问私有变量）
                    field.setAccessible(true);
                    // 12、如果属性是@RequestKeyParam注解，则拼接连接符"-" + RequestKeyParam
                    sb.append(requestLock.delimiter()).append(ReflectionUtils.getField(field, object));
                }
            }
        }
        // 13、返回指定前缀的唯一key
        return requestLock.prefixKey() + sb;
    }
}
