package com.example.payday.coupon.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class CouponIssueResponseDto {
    private Long couponId;
    private String couponName;
    private Long userId;
}
