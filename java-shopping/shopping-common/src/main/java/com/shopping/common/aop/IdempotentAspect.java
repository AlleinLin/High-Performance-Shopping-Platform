package com.shopping.common.aop;

import com.shopping.common.annotation.Idempotent;
import com.shopping.common.constant.RedisConstants;
import com.shopping.common.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.lang.reflect.Method;
import java.util.Objects;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class IdempotentAspect {

    private final org.springframework.data.redis.core.StringRedisTemplate redisTemplate;

    @Around("@annotation(com.shopping.common.annotation.Idempotent)")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        Idempotent idempotent = method.getAnnotation(Idempotent.class);

        String key = buildKey(idempotent, joinPoint);
        long expireTime = idempotent.expireTime();

        Boolean success = redisTemplate.opsForValue()
                .setIfAbsent(key, "1", expireTime, idempotent.timeUnit());

        if (Boolean.FALSE.equals(success)) {
            log.warn("Idempotent check failed for key: {}", key);
            throw new BusinessException(idempotent.message());
        }

        try {
            return joinPoint.proceed();
        } catch (Exception e) {
            redisTemplate.delete(key);
            throw e;
        }
    }

    private String buildKey(Idempotent idempotent, ProceedingJoinPoint joinPoint) {
        String prefix = idempotent.prefix();
        String key = idempotent.key();

        if (key.isEmpty()) {
            HttpServletRequest request = ((ServletRequestAttributes) Objects.requireNonNull(
                    RequestContextHolder.getRequestAttributes())).getRequest();
            key = request.getRequestURI() + ":" + getClientIp(request);
        }

        return RedisConstants.IDEMPOTENT_PREFIX + prefix + ":" + key;
    }

    private String getClientIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("X-Real-IP");
        }
        if (ip == null || ip.isEmpty() || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }
}
