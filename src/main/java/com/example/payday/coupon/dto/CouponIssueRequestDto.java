package com.example.payday.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "쿠폰 발급 요청 DTO")
public class CouponIssueRequestDto {

    @Schema(description = "쿠폰 템플릿 ID", example = "1")
    @NotNull(message = "쿠폰 템플릿 ID는 필수입니다.")
    private Long templateId;

    @Schema(description = "사용자 ID", example = "1")
    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;
}
