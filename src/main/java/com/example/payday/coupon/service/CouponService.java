package com.example.payday.coupon.service;

import com.example.payday.coupon.domain.Coupon;
import com.example.payday.coupon.dto.CouponApplyRequestDto;
import com.example.payday.coupon.dto.CouponResponseDto;
import com.example.payday.coupon.exception.*;
import com.example.payday.coupon.mapper.CouponMapper;
import com.example.payday.coupon.policy.CouponDiscountPolicy;
import com.example.payday.coupon.policy.CouponDiscountPolicyFactory;
import com.example.payday.coupon.repository.CouponRepository;
import com.example.payday.user.domain.User;
import com.example.payday.user.exception.UserNotFoundException;
import com.example.payday.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponDiscountPolicyFactory policyFactory;
    private final UserRepository userRepository; // ✅ 반드시 주입 필요

    /**
     * 내부 결제 로직 등에서 금액 반환용 (단순 금액 계산 + 사용 처리)
     */
    @Transactional
    public int applyDiscountForPayment(Long couponId, int originalAmount) {
        Coupon coupon = getValidatedCoupon(couponId, originalAmount);
        int discountedAmount = calculateDiscount(coupon, originalAmount);

        coupon.markUsed();
        return originalAmount - discountedAmount;
    }

    /**
     * 단일 쿠폰 ID로 조회
     */
    @Transactional(readOnly = true)
    public Coupon getCouponById(Long id) {
        return couponRepository.findById(id)
                .orElseThrow(CouponNotFoundException::new);
    }

    /**
     * 쿠폰 미리보기 (할인 금액 계산)
     */
    @Transactional(readOnly = true)
    public CouponResponseDto previewCoupon(CouponApplyRequestDto request) {
        Coupon coupon = getValidatedCoupon(request.getCouponId(), request.getOriginalAmount());

        int discountAmount = calculateDiscount(coupon, request.getOriginalAmount());
        int discountedAmount = request.getOriginalAmount() - discountAmount;

        return CouponMapper.toResponseDto(coupon, request.getOriginalAmount(), discountedAmount);
    }

    /**
     * 사용자의 발급된 쿠폰 전체 조회
     */
    @Transactional(readOnly = true)
    public Page<CouponResponseDto> getCouponsByUser(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Page<Coupon> coupons = couponRepository.findByUser(user, pageable);

        // ✅ Page<Coupon> → Page<CouponResponseDto> 로 변환
        return coupons.map(c -> CouponMapper.toResponseDto(c, 0, 0)); // 할인 미리보기 적용 안함
    }

    // ------------------- private -----------------------

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
