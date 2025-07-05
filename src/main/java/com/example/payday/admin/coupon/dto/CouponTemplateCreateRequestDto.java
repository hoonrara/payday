package com.example.payday.admin.coupon.dto;

import com.example.payday.coupon.domain.CouponType;
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
public class CouponTemplateCreateRequestDto {

    @NotBlank(message = "쿠폰 이름은 필수입니다.")
    private String name;

    @NotNull(message = "할인 타입은 필수입니다.")
    private CouponType discountType;

    @Positive(message = "할인 금액은 0보다 커야 합니다.")
    private int amount;

    private Integer maxDiscountAmount; // 선택값이라면 유효성 생략 가능

    @PositiveOrZero(message = "최소 주문 금액은 0 이상이어야 합니다.")
    private int minOrderAmount;

    @NotNull(message = "만료일시는 필수입니다.")
    private LocalDateTime expiredAt;
}
