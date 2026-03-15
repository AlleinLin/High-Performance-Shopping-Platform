package com.shopping.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserStatus {

    NORMAL(0, "Normal"),
    LOCKED(1, "Locked"),
    DISABLED(2, "Disabled");

    private final Integer code;
    private final String description;

    public static UserStatus fromCode(Integer code) {
        for (UserStatus status : values()) {
            if (status.getCode().equals(code)) {
                return status;
            }
        }
        return null;
    }
}
