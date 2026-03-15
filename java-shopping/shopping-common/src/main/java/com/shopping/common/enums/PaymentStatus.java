package com.shopping.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentStatus {

    PENDING(0, "Pending"),
    PROCESSING(1, "Processing"),
    SUCCESS(2, "Success"),
    FAILED(3, "Failed"),
    TIMEOUT(4, "Timeout"),
    CANCELLED(5, "Cancelled"),
    REFUNDING(6, "Refunding"),
    REFUNDED(7, "Refunded");

    private final Integer code;
    private final String description;

    public static PaymentStatus fromCode(Integer code) {
        for (PaymentStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
