package com.example.payday.coupon.policy;

import com.example.payday.coupon.domain.CouponType;
import com.example.payday.coupon.exception.UnsupportedCouponTypeException;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
@RequiredArgsConstructor
public class CouponDiscountPolicyFactory {

    private final Map<String, CouponDiscountPolicy> policyMap;

    public CouponDiscountPolicy getPolicy(CouponType type) {
        CouponDiscountPolicy policy = policyMap.get(type.name());
        if (policy == null) throw new UnsupportedCouponTypeException();
        return policy;
    }
}
