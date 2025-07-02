package com.example.payday.coupon.exception;

import com.example.payday.global.exception.ErrorCode;
import com.example.payday.global.exception.base.BusinessException;

public class CouponMinimumAmountException extends BusinessException {
    public CouponMinimumAmountException() {
        super(ErrorCode.COUPON_MINIMUM_AMOUNT);
    }
}