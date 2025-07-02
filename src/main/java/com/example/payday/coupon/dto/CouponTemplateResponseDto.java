package com.example.payday.coupon.dto;

import com.example.payday.coupon.domain.CouponType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CouponTemplateResponseDto {
    private Long id;
    private String name;
    private CouponType type;
    private int amount;
    private Integer maxDiscountAmount;
    private int minOrderAmount;
    private LocalDateTime expiredAt;
}