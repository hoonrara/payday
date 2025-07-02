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

    /**
     * 내부 결제 로직 등에서 금액 반환용 (단순 금액 계산 + 사용 처리)
     */
    @Transactional
    public int applyDiscountForPayment(Long couponId, int originalAmount) {
        Coupon coupon = getValidatedCoupon(couponId, originalAmount);
        int discountedAmount = calculateDiscount(coupon, originalAmount);

        coupon.markUsed();
        return discountedAmount;
    }

    @Transactional(readOnly = true)
    public Coupon getCouponById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(CouponNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public CouponResponseDto previewCoupon(CouponApplyRequestDto request) {
        Coupon coupon = getValidatedCoupon(request.getCouponId(), request.getOriginalAmount());
        int discountedAmount = calculateDiscount(coupon, request.getOriginalAmount());

        return CouponMapper.toResponseDto(coupon, request.getOriginalAmount(), discountedAmount);
    }

    // ------------------- private 로직 -----------------------

    private Coupon getValidatedCoupon(Long couponId, int originalAmount) {
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(CouponNotFoundException::new);

        validateCoupon(originalAmount, coupon);
        return coupon;
    }

    private int calculateDiscount(Coupon coupon, int originalAmount) {
        CouponDiscountPolicy policy = policyFactory.getPolicy(coupon.getType());
        return policy.calculateDiscount(
                originalAmount,
                coupon.getAmount(),
                coupon.getMaxDiscountAmount()
        );
    }

    private void validateCoupon(int originalAmount, Coupon coupon) {
        if (!coupon.isValid()) {
            if (coupon.isUsed()) {
                throw new CouponAlreadyUsedException();
            }
            throw new CouponExpiredException();
        }

        if (originalAmount < coupon.getMinOrderAmount()) {
            throw new CouponMinimumAmountException();
        }
    }
}
