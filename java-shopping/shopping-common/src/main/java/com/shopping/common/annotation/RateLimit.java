package com.shopping.common.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface RateLimit {

    String key() default "";

    int limit() default 100;

    int period() default 60;

    TimeUnit timeUnit() default TimeUnit.SECONDS;

    String message() default "Too many requests, please try again later";
}
