package com.shopping.common.result;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode {

    SUCCESS(200, "Operation successful"),
    FAILED(500, "Operation failed"),
    VALIDATE_FAILED(400, "Validation failed"),
    UNAUTHORIZED(401, "Not logged in or token expired"),
    FORBIDDEN(403, "No permission to access"),
    NOT_FOUND(404, "Resource not found"),
    TOO_MANY_REQUESTS(429, "Too many requests"),
    SERVICE_UNAVAILABLE(503, "Service unavailable"),

    USER_NOT_FOUND(1001, "User not found"),
    USER_EXISTS(1002, "User already exists"),
    PASSWORD_ERROR(1003, "Password incorrect"),
    ACCOUNT_DISABLED(1004, "Account is disabled"),
    ACCOUNT_LOCKED(1005, "Account is locked"),
    VERIFICATION_CODE_ERROR(1006, "Verification code error"),
    VERIFICATION_CODE_EXPIRED(1007, "Verification code expired"),

    PRODUCT_NOT_FOUND(2001, "Product not found"),
    PRODUCT_OFF_SHELF(2002, "Product is off shelf"),
    PRODUCT_STOCK_NOT_ENOUGH(2003, "Product stock not enough"),
    CATEGORY_NOT_FOUND(2004, "Category not found"),
    BRAND_NOT_FOUND(2005, "Brand not found"),

    CART_EMPTY(3001, "Shopping cart is empty"),
    CART_ITEM_NOT_FOUND(3002, "Cart item not found"),
    PRODUCT_NOT_IN_CART(3003, "Product not in cart"),

    ORDER_NOT_FOUND(4001, "Order not found"),
    ORDER_STATUS_ERROR(4002, "Order status error"),
    ORDER_ALREADY_PAID(4003, "Order already paid"),
    ORDER_ALREADY_CANCELLED(4004, "Order already cancelled"),
    ORDER_CANNOT_CANCEL(4005, "Order cannot be cancelled"),
    ORDER_EXPIRED(4006, "Order expired"),

    PAYMENT_FAILED(5001, "Payment failed"),
    PAYMENT_TIMEOUT(5002, "Payment timeout"),
    REFUND_FAILED(5003, "Refund failed"),

    INVENTORY_NOT_ENOUGH(6001, "Inventory not enough"),
    INVENTORY_LOCKED(6002, "Inventory locked"),
    INVENTORY_UNLOCK_FAILED(6003, "Inventory unlock failed");

    private final Integer code;
    private final String message;
}
