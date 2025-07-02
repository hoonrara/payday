package com.example.payday.coupon.mapper;

import com.example.payday.coupon.domain.Coupon;
import com.example.payday.coupon.dto.CouponCreateRequestDto;
import com.example.payday.coupon.dto.CouponResponseDto;

public class CouponMapper {

    public static Coupon toEntity(CouponCreateRequestDto dto) {
        return Coupon.builder()
                .name(dto.getName())
                .type(dto.getType())
                .amount(dto.getAmount())
                .expiredAt(dto.getExpiredAt())
                .minOrderAmount(dto.getMinOrderAmount())
                .maxDiscountAmount(dto.getMaxDiscountAmount())
                .user(null) // 인증/인가 생략 조건
                .build();
    }

    public static CouponResponseDto toResponseDto(Coupon coupon, int originalAmount, int discountedAmount) {
        return CouponResponseDto.builder()
                .couponId(coupon.getId())
                .couponName(coupon.getType().name())
                .originalAmount(originalAmount)
                .discountedAmount(discountedAmount)
                .build();
    }
}