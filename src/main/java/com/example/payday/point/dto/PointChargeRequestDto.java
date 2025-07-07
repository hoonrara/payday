package com.example.payday.point.dto;


import com.example.payday.payment.type.PaymentMethod;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "포인트 충전 요청 DTO")
public class PointChargeRequestDto {

    @Schema(description = "유저 ID", example = "1")
    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    @Schema(description = "결제 키 (Toss 등)", example = "pay_test_1234567890")
    @NotBlank(message = "결제 키는 필수입니다.")
    private String paymentKey;

    @Schema(description = "충전 포인트", example = "10000")
    @Positive(message = "충전 포인트는 0보다 커야 합니다.")
    private int pointAmount;

    @Schema(description = "결제 수단", example = "toss")
    @NotBlank(message = "결제 수단은 필수입니다.")
    private String method;

    @Schema(description = "쿠폰 ID (선택)", example = "5")
    private Long couponId;
}
