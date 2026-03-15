package com.shopping.common.constant;

public interface RedisConstants {

    String PREFIX = "shopping:";

    String USER_TOKEN_PREFIX = PREFIX + "user:token:";
    String USER_INFO_PREFIX = PREFIX + "user:info:";
    String USER_PERMISSION_PREFIX = PREFIX + "user:permission:";

    String PRODUCT_INFO_PREFIX = PREFIX + "product:info:";
    String PRODUCT_STOCK_PREFIX = PREFIX + "product:stock:";
    String PRODUCT_CATEGORY_PREFIX = PREFIX + "product:category:";
    String PRODUCT_BRAND_PREFIX = PREFIX + "product:brand:";
    String PRODUCT_HOT_PREFIX = PREFIX + "product:hot:";
    String PRODUCT_NEW_PREFIX = PREFIX + "product:new:";
    String PRODUCT_RECOMMEND_PREFIX = PREFIX + "product:recommend:";

    String CART_PREFIX = PREFIX + "cart:";
    String CART_COUNT_PREFIX = PREFIX + "cart:count:";

    String ORDER_PREFIX = PREFIX + "order:";
    String ORDER_USER_PREFIX = PREFIX + "order:user:";
    String ORDER_SN_PREFIX = PREFIX + "order:sn:";

    String INVENTORY_LOCK_PREFIX = PREFIX + "inventory:lock:";
    String INVENTORY_STOCK_PREFIX = PREFIX + "inventory:stock:";

    String CAPTCHA_PREFIX = PREFIX + "captcha:";
    String SMS_CODE_PREFIX = PREFIX + "sms:code:";
    String EMAIL_CODE_PREFIX = PREFIX + "email:code:";

    String RATE_LIMIT_PREFIX = PREFIX + "rate:limit:";
    String IDEMPOTENT_PREFIX = PREFIX + "idempotent:";

    String DISTRIBUTED_LOCK_PREFIX = PREFIX + "lock:";

    String BLOOM_FILTER_PREFIX = PREFIX + "bloom:";

    long DEFAULT_EXPIRE_TIME = 3600L;
    long TOKEN_EXPIRE_TIME = 86400L;
    long CAPTCHA_EXPIRE_TIME = 300L;
    long SMS_CODE_EXPIRE_TIME = 300L;
    long CART_EXPIRE_TIME = 604800L;
    long PRODUCT_INFO_EXPIRE_TIME = 3600L;
    long ORDER_EXPIRE_TIME = 1800L;
    long RATE_LIMIT_EXPIRE_TIME = 60L;
}
