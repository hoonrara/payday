package com.example.payday.coupon.policy;

import com.example.payday.coupon.domain.CouponType;
import com.example.payday.coupon.exception.UnsupportedCouponTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CouponDiscountPolicyFactory {

    private final ApplicationContext context;

    public CouponDiscountPolicy getPolicy(CouponType type) {
        try {
            return (CouponDiscountPolicy) context.getBean(type.name());
        } catch (Exception e) {
            throw new UnsupportedCouponTypeException();
        }
    }
}