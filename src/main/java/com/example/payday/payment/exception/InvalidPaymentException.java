package com.example.payday.payment.exception;

import com.example.payday.global.exception.ErrorCode;
import com.example.payday.global.exception.base.BusinessException;

public class InvalidPaymentException extends BusinessException {
    public InvalidPaymentException() {
        super(ErrorCode.INVALID_PAYMENT);
    }
}