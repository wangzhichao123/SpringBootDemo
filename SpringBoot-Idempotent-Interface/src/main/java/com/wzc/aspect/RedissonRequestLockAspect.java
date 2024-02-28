package com.wzc.aspect;

import com.wzc.annotation.RequestLock;
import com.wzc.exception.BizException;
import com.wzc.util.RequestKeyGenerator;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Method;

/**
 * description 分布式锁实现
 */
@Aspect
@Configuration
@Order(0)
public class RedissonRequestLockAspect {
    @Resource
    private RedissonClient redissonClient;

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
        // 2、使用Redisson分布式锁的方式判断是否重复提交（获取锁对象）
        RLock lock = redissonClient.getLock(lockKey);
        boolean isLocked = false;
        try {
            // 3、尝试加锁
            isLocked = lock.tryLock();
            // 4、没有拿到锁说明已经有了请求了
            if (!isLocked) {
                throw new BizException("您的操作太快了,请稍后重试");
            }
            // 4、拿到锁后设置过期时间
            lock.lock(requestLock.expire(), requestLock.timeUnit());
            try {
                return joinPoint.proceed();
            } catch (Throwable throwable) {
                throw new BizException("系统异常");
            }
        } catch (Exception e) {
            throw new BizException("您的操作太快了,请稍后重试");
        } finally {
            // 5、释放锁（如果处于上锁状态且是当前线程持有锁，则释放锁）
            if (isLocked && lock.isHeldByCurrentThread()) {
                lock.unlock();
            }
        }

    }
}
