package com.example.payday.coupon.exception;

import com.example.payday.global.exception.ErrorCode;
import com.example.payday.global.exception.base.BusinessException;

public class AlreadyIssuedCouponException extends BusinessException {
    public AlreadyIssuedCouponException() {
        super(ErrorCode.ALREADY_ISSUED_COUPON);
    }
}