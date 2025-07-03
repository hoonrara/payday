package com.example.payday.coupon.mapper;

import com.example.payday.coupon.domain.Coupon;
import com.example.payday.coupon.domain.CouponTemplate;
import com.example.payday.coupon.dto.CouponResponseDto;
import com.example.payday.user.domain.User;

public class CouponMapper {

    public static CouponResponseDto toResponseDto(Coupon coupon, int originalAmount, int discountedAmount) {
        return CouponResponseDto.builder()
                .couponId(coupon.getId())
                .couponName(coupon.getName())
                .originalAmount(originalAmount)
                .discountedAmount(discountedAmount)
                .build();
    }

    public static Coupon fromTemplate(CouponTemplate template, User user) {
        return Coupon.builder()
                .name(template.getName())
                .type(template.getType())
                .amount(template.getAmount())
                .maxDiscountAmount(template.getMaxDiscountAmount())
                .minOrderAmount(template.getMinOrderAmount())
                .expiredAt(template.getExpiredAt())
                .user(user)
                .template(template)
                .build();
    }
}