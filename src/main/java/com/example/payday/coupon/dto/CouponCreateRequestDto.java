package com.example.payday.coupon.dto;

import com.example.payday.coupon.domain.CouponType;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class CouponCreateRequestDto {
    private String name;
    private CouponType type;
    private int amount;
    private LocalDateTime expiredAt;
    private int minOrderAmount;
    private Integer maxDiscountAmount;
}