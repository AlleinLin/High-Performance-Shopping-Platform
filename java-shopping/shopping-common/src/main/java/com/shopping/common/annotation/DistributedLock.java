package com.shopping.common.annotation;

import java.lang.annotation.*;

@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DistributedLock {

    String key() default "";

    String prefix() default "lock";

    long waitTime() default 0;

    long leaseTime() default 30;

    String message() default "System busy, please try again later";
}
