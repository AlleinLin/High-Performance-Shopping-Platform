package com.shopping.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProductStatus {

    DRAFT(0, "Draft"),
    PENDING_REVIEW(1, "Pending Review"),
    ON_SHELF(2, "On Shelf"),
    OFF_SHELF(3, "Off Shelf"),
    DELETED(4, "Deleted");

    private final Integer code;
    private final String description;

    public static ProductStatus fromCode(Integer code) {
        for (ProductStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
