package com.example.payday.coupon.dto;

import lombok.Builder;
import lombok.Getter;


@Getter
@Builder
public class CouponResponseDto {

    private Long couponId;
    private String couponName;
    private int originalAmount;
    private int discountedAmount;
}
