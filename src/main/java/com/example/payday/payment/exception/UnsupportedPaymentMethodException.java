package com.example.payday.payment.exception;

import com.example.payday.global.exception.ErrorCode;
import com.example.payday.global.exception.base.BusinessException;

public class UnsupportedPaymentMethodException extends BusinessException {
    public UnsupportedPaymentMethodException() {
        super(ErrorCode.UNSUPPORTED_PAYMENT_METHOD);
    }
}