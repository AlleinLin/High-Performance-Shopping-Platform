package com.shopping.common.aop;

import com.shopping.common.annotation.RateLimit;
import com.shopping.common.constant.RedisConstants;
import com.shopping.common.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.concurrent.TimeUnit;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class RateLimitAspect {

    private final StringRedisTemplate redisTemplate;

    @Around("@annotation(com.shopping.common.annotation.RateLimit)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        RateLimit rateLimit = method.getAnnotation(RateLimit.class);

        String key = buildKey(rateLimit, joinPoint);
        long period = rateLimit.period();
        int limit = rateLimit.limit();

        Long count = redisTemplate.opsForValue().increment(key);
        if (count != null && count == 1) {
            redisTemplate.expire(key, period, rateLimit.timeUnit());
        }

        if (count != null && count > limit) {
            log.warn("Rate limit exceeded for key: {}, count: {}", key, count);
            throw new BusinessException(rateLimit.message());
        }

        return joinPoint.proceed();
    }

    private String buildKey(RateLimit rateLimit, ProceedingJoinPoint joinPoint) {
        String key = rateLimit.key();
        if (key.isEmpty()) {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            key = signature.getDeclaringTypeName() + ":" + signature.getName();
        }
        return RedisConstants.RATE_LIMIT_PREFIX + key;
    }
}
