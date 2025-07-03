package com.example.payday.payment.type;

import com.example.payday.payment.exception.UnsupportedPaymentMethodException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum PaymentMethod {
    TOSS("toss"),
    FAKE("fake");

    private final String gatewayBeanName;

    public String getBeanName() {
        return gatewayBeanName;
    }

    public static PaymentMethod from(String value) {
        for (PaymentMethod method : PaymentMethod.values()) {
            if (method.gatewayBeanName.equalsIgnoreCase(value)) {
                return method;
            }
        }
        throw new UnsupportedPaymentMethodException();
    }
}