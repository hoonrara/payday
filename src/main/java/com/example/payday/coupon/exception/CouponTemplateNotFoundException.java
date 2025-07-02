package com.example.payday.coupon.exception;

import com.example.payday.global.exception.ErrorCode;
import com.example.payday.global.exception.base.BusinessException;

public class CouponTemplateNotFoundException extends BusinessException {
    public CouponTemplateNotFoundException() {
        super(ErrorCode.COUPON_TEMPLATE_NOT_FOUND);
    }
}
