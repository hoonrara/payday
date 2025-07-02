package com.example.payday.coupon.exception;

import com.example.payday.global.exception.ErrorCode;
import com.example.payday.global.exception.base.BusinessException;

public class CouponExpiredException extends BusinessException {
  public CouponExpiredException() {
    super(ErrorCode.COUPON_EXPIRED);
  }
}