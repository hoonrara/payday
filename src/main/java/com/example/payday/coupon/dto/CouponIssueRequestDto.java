package com.example.payday.coupon.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponIssueRequestDto {

    @NotNull(message = "쿠폰 템플릿 ID는 필수입니다.")
    private Long templateId;

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;
}
