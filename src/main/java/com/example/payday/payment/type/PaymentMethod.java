package com.example.payday.payment.type;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentMethod {
    TOSS("toss"),
    FAKE("fake");

    private final String value;

    public String getValue() {
        return value;
    }

    public static PaymentMethod from(String value) {
        for (PaymentMethod method : PaymentMethod.values()) {
            if (method.getValue().equalsIgnoreCase(value)) {
                return method;
            }
        }
        throw new IllegalArgumentException("지원하지 않는 결제 방식입니다: " + value);
    }
}