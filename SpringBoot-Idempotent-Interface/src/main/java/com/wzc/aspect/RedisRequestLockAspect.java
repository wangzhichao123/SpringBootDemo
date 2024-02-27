package com.wzc.aspect;

import com.wzc.annotation.RequestLock;
import com.wzc.exception.BizException;
import com.wzc.util.RequestKeyGenerator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

/**
 * description 缓存实现
 */
@Aspect
@Configuration
@Order(0)
public class RedisRequestLockAspect {

    @Resource
    private StringRedisTemplate stringRedisTemplate;

    @Around("execution(public * * (..)) && @annotation(com.wzc.annotation.RequestLock)")
    public Object interceptor(ProceedingJoinPoint joinPoint) {
        MethodSignature methodSignature = (MethodSignature)joinPoint.getSignature();
        Method method = methodSignature.getMethod();
        RequestLock requestLock = method.getAnnotation(RequestLock.class);
        if (StringUtils.isEmpty(requestLock.prefixKey())) {
            throw new BizException("重复提交前缀不能为空");
        }
        // 1、获取唯一Key
        final String lockKey = RequestKeyGenerator.getLockKey(joinPoint);
        // 2、需要保证原子操作
        Boolean success = stringRedisTemplate.opsForValue().setIfAbsent(lockKey, "", requestLock.time(), requestLock.unit());
        if (Boolean.FALSE.equals(success)) {
            throw new BizException("您的操作太快了,请稍后重试");
        }
        try {
            return joinPoint.proceed();
        } catch (Throwable throwable) {
            throw new BizException("系统异常");
        }
    }
}
