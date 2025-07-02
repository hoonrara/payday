package com.example.payday.coupon.policy;

public interface CouponDiscountPolicy {
    int calculateDiscount(int originalAmount, int discountValue, Integer maxDiscountAmount);
}