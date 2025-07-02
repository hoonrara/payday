package com.example.payday.coupon.policy;

import org.springframework.stereotype.Component;

@Component("FIXED")
public class FixedAmountCouponPolicy implements CouponDiscountPolicy {

    @Override
    public int calculateDiscount(int originalAmount, int discountValue, Integer maxDiscountAmount) {
        return Math.max(0, originalAmount - discountValue);
    }
}
