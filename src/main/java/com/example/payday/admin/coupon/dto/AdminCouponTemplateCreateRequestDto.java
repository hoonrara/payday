package com.example.payday.admin.coupon.dto;

import com.example.payday.coupon.domain.CouponType;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "쿠폰 템플릿 생성 요청 DTO")
public class AdminCouponTemplateCreateRequestDto {

    @Schema(description = "쿠폰 이름", example = "여름 이벤트 할인쿠폰")
    @NotBlank(message = "쿠폰 이름은 필수입니다.")
    private String name;

    @Schema(description = "할인 타입 (정액 할인 / 정률 할인)", example = "RATE")
    @NotNull(message = "할인 타입은 필수입니다.")
    private CouponType discountType;

    @Schema(description = "할인 금액 (정액일 경우 금액, 정률일 경우 퍼센트)", example = "20")
    @Positive(message = "할인 금액은 0보다 커야 합니다.")
    private int amount;

    @Schema(description = "최대 할인 금액 (정률일 경우만 필요)", example = "5000", nullable = true)
    private Integer maxDiscountAmount;

    @Schema(description = "최소 주문 금액", example = "10000")
    @PositiveOrZero(message = "최소 주문 금액은 0 이상이어야 합니다.")
    private int minOrderAmount;

    @Schema(description = "최대 발급 수 (선착순 쿠폰일 경우만 사용)", example = "10", nullable = true)
    private Integer maxIssueCount;

    @Schema(description = "자동 발급 여부", example = "true")
    private boolean autoIssue;

    @Schema(description = "만료 일시", example = "2025-08-01T23:59:59")
    @NotNull(message = "만료일시는 필수입니다.")
    private LocalDateTime expiredAt;
}