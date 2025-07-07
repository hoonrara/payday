package com.example.payday.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "쿠폰 적용 요청 DTO")
public class CouponApplyRequestDto {

    @Schema(description = "사용자 ID", example = "1")
    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    @Schema(description = "쿠폰 ID", example = "10")
    @NotNull(message = "쿠폰 ID는 필수입니다.")
    private Long couponId;

    @Schema(description = "결제 금액", example = "10000")
    @Positive(message = "결제 금액은 0보다 커야 합니다.")
    private int originalAmount;
}