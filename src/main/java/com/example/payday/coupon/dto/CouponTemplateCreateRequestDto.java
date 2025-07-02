package com.example.payday.coupon.dto;

import com.example.payday.coupon.domain.CouponType;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CouponTemplateCreateRequestDto {
    private String name;
    private CouponType type;
    private int amount;
    private Integer maxDiscountAmount;
    private int minOrderAmount;
    private LocalDateTime expiredAt;
}
