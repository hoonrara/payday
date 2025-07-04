package com.example.payday.coupon.dto;

import com.example.payday.coupon.domain.CouponType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponTemplateCreateRequestDto {
    private String name;
    private CouponType discountType;
    private int amount;
    private Integer maxDiscountAmount;
    private int minOrderAmount;
    private LocalDateTime expiredAt;
}
