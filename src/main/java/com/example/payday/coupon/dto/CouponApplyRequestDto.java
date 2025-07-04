package com.example.payday.coupon.dto;

import lombok.Builder;
import lombok.Getter;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponApplyRequestDto {

    private Long userId;
    private Long couponId;
    private int originalAmount;
}