package com.example.payday.admin.coupon.dto;

import com.example.payday.coupon.domain.CouponType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "쿠폰 템플릿 응답 DTO")
public class AdminCouponTemplateResponseDto {

    @Schema(description = "쿠폰 템플릿 ID", example = "1")
    private Long id;

    @Schema(description = "쿠폰 이름", example = "여름맞이 할인쿠폰")
    private String name;

    @Schema(description = "할인 타입 (정액 할인 / 정률 할인)", example = "정률 할인")
    private CouponType discountType;

    @Schema(description = "할인 금액 또는 할인 비율", example = "20")
    private int amount;

    @Schema(description = "최대 할인 금액 (정률일 경우)", example = "5000", nullable = true)
    private Integer maxDiscountAmount;

    @Schema(description = "최소 주문 금액", example = "10000")
    private int minOrderAmount;

    @Schema(description = "만료 일시", example = "2025-08-01T23:59:59")
    private LocalDateTime expiredAt;
}