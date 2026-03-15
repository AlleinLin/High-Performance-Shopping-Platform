package com.shopping.common.annotation;

import java.lang.annotation.*;
import java.util.concurrent.TimeUnit;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Idempotent {

    String key() default "";

    String prefix() default "idempotent";

    long expireTime() default 5;

    TimeUnit timeUnit() default TimeUnit.SECONDS;

    String message() default "Duplicate request, please try again later";
}
