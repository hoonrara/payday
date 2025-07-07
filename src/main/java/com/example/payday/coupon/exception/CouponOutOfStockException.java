package com.example.payday.coupon.exception;

import com.example.payday.global.exception.ErrorCode;
import com.example.payday.global.exception.base.BusinessException;

public class CouponOutOfStockException extends BusinessException {
    public CouponOutOfStockException() {
        super(ErrorCode.COUPON_OUT_OF_STOCK);
    }
}