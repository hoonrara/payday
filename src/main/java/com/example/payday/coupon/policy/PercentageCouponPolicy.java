package com.example.payday.coupon.policy;

import org.springframework.stereotype.Component;

@Component("PERCENTAGE")
public class PercentageCouponPolicy implements CouponDiscountPolicy {

    @Override
    public int calculateDiscount(int originalAmount, int discountValue, Integer maxDiscountAmount) {
        int discount = (int) (originalAmount * (discountValue / 100.0));
        if (maxDiscountAmount != null) {
            discount = Math.min(discount, maxDiscountAmount);
        }
        return discount;
    }
}