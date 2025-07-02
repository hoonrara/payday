package com.example.payday.coupon.exception;

import com.example.payday.global.exception.ErrorCode;
import com.example.payday.global.exception.base.BusinessException;

public class CouponAlreadyUsedException extends BusinessException {
    public CouponAlreadyUsedException() {
        super(ErrorCode.COUPON_ALREADY_USED);
    }
}
