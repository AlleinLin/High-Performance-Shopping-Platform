package com.shopping.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStatus {

    PENDING_PAYMENT(0, "Pending Payment"),
    PAID(1, "Paid"),
    PENDING_SHIPMENT(2, "Pending Shipment"),
    SHIPPED(3, "Shipped"),
    DELIVERED(4, "Delivered"),
    CANCELLED(5, "Cancelled"),
    REFUNDING(6, "Refunding"),
    REFUNDED(7, "Refunded"),
    CLOSED(8, "Closed");

    private final Integer code;
    private final String description;

    public static OrderStatus fromCode(Integer code) {
        for (OrderStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }

    public boolean canCancel() {
        return this == PENDING_PAYMENT || this == PAID || this == PENDING_SHIPMENT;
    }

    public boolean canPay() {
        return this == PENDING_PAYMENT;
    }

    public boolean canShip() {
        return this == PAID || this == PENDING_SHIPMENT;
    }

    public boolean canConfirm() {
        return this == SHIPPED;
    }

    public boolean canRefund() {
        return this == PAID || this == PENDING_SHIPMENT || this == SHIPPED;
    }
}
