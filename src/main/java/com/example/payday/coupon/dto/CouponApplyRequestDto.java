package com.example.payday.coupon.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;


import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponApplyRequestDto {

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    @NotNull(message = "쿠폰 ID는 필수입니다.")
    private Long couponId;

    @Positive(message = "결제 금액은 0보다 커야 합니다.")
    private int originalAmount;
}