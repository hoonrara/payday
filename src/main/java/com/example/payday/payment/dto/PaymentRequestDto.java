package com.example.payday.payment.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "결제 요청 DTO")
public class PaymentRequestDto {

    @Schema(description = "결제 키 (Toss 등에서 발급)", example = "pay_test_1234567890")
    private String paymentKey;

    @Schema(description = "주문 ID", example = "ORDER_20250701_0001")
    private String orderId;

    @Schema(description = "결제 금액", example = "10000")
    private int amount;

    @Schema(description = "결제 수단", example = "toss")
    private String method;
}
