package com.shopping.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PaymentMethod {

    ALIPAY(1, "Alipay"),
    WECHAT_PAY(2, "WeChat Pay"),
    BANK_CARD(3, "Bank Card"),
    BALANCE(4, "Balance"),
    CREDIT_CARD(5, "Credit Card");

    private final Integer code;
    private final String description;

    public static PaymentMethod fromCode(Integer code) {
        for (PaymentMethod method : values()) {
            if (method.getCode().equals(code)) {
                return method;
            }
        }
        return null;
    }
}
