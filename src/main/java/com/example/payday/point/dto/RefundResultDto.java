package com.example.payday.point.dto;

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
@Schema(description = "환불 결과 DTO")
public class RefundResultDto {

    @Schema(description = "결제 키 또는 주문 ID", example = "ORDER_20250705_145425_1")
    private String paymentKey;

    @Schema(description = "환불 금액", example = "10000")
    private int cancelAmount;

    @Schema(description = "환불 사유", example = "사용자 요청")
    private String cancelReason;

    @Schema(description = "환불 시간", example = "2025-07-05T14:54:41.496")
    private LocalDateTime canceledAt;

    @Schema(description = "환불 상태", example = "CANCELED")
    private String status;
}