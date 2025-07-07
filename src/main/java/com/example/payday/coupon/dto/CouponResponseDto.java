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
@Schema(description = "쿠폰 할인 결과 응답 DTO")
public class CouponResponseDto {

    @Schema(description = "쿠폰 ID", example = "100")
    private Long couponId;

    @Schema(description = "쿠폰 이름", example = "가입 축하 20% 할인쿠폰")
    private String couponName;

    @Schema(description = "원래 결제 금액", example = "10000")
    private int originalAmount;

    @Schema(description = "할인 적용 후 금액", example = "8000")
    private int discountedAmount;
}
