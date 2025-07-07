package com.example.payday.payment.dto;

import com.example.payday.payment.type.PaymentStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "결제 결과 응답 DTO")
public class PaymentResultDto {

    @Schema(description = "결제 키", example = "pay_test_1234567890")
    private String paymentKey;

    @Schema(description = "주문 ID", example = "ORDER_20250701_0001")
    private String orderId;

    @Schema(description = "결제 금액", example = "10000")
    private int amount;

    @Schema(description = "결제 상태", example = "SUCCESS")
    private PaymentStatus status;

    @Schema(description = "승인 시간", example = "2025-07-01T12:00:00")
    private LocalDateTime approvedAt;
}