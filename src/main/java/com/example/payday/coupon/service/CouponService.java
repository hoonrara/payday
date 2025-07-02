package com.example.payday.coupon.service;

import com.example.payday.coupon.domain.Coupon;
import com.example.payday.coupon.dto.CouponApplyRequestDto;
import com.example.payday.coupon.dto.CouponResponseDto;
import com.example.payday.coupon.exception.*;
import com.example.payday.coupon.mapper.CouponMapper;
import com.example.payday.coupon.policy.CouponDiscountPolicy;
import com.example.payday.coupon.policy.CouponDiscountPolicyFactory;
import com.example.payday.coupon.repository.CouponRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponDiscountPolicyFactory policyFactory;

    @Transactional(readOnly = true)
    public CouponResponseDto applyCoupon(CouponApplyRequestDto request) {
        Coupon coupon = couponRepository.findById(request.getCouponId())
                .orElseThrow(CouponNotFoundException::new);

        if (!coupon.isValid()) {
            if (coupon.isUsed()) {
                throw new CouponAlreadyUsedException();
            }
            throw new CouponExpiredException();
        }

        if (request.getOriginalAmount() < coupon.getMinOrderAmount()) {
            throw new CouponMinimumAmountException();
        }

        CouponDiscountPolicy policy = policyFactory.getPolicy(coupon.getType());
        int discountedAmount = policy.calculateDiscount(
                request.getOriginalAmount(),
                coupon.getAmount(),
                coupon.getMaxDiscountAmount()
        );

        return CouponMapper.toResponseDto(coupon, request.getOriginalAmount(), discountedAmount);
    }
}
