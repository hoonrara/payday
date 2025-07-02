package com.example.payday.coupon.exception;

import com.example.payday.global.exception.ErrorCode;
import com.example.payday.global.exception.base.BusinessException;

public class UnsupportedCouponTypeException extends BusinessException {
    public UnsupportedCouponTypeException() {
        super(ErrorCode.UNSUPPORTED_COUPON_TYPE);
    }
}