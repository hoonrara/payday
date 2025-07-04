package com.example.payday.coupon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponseDto {

    private Long couponId;
    private String couponName;
    private int originalAmount;
    private int discountedAmount;
}
