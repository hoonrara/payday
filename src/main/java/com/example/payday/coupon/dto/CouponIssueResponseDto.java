package com.example.payday.coupon.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "쿠폰 발급 응답 DTO")
public class CouponIssueResponseDto {

    @Schema(description = "발급된 쿠폰 ID", example = "100")
    private Long couponId;

    @Schema(description = "쿠폰 이름", example = "가입 축하 20% 할인쿠폰")
    private String couponName;

    @Schema(description = "사용자 ID", example = "1")
    private Long userId;
}
