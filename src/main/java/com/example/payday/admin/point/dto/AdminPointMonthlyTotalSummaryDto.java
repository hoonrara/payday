package com.example.payday.admin.point.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "관리자 - 월간 총 포인트 통계 DTO")
public class AdminPointMonthlyTotalSummaryDto {

    @Schema(description = "총 충전 금액", example = "100000")
    private int totalChargeAmount;

    @Schema(description = "총 환불 금액", example = "20000")
    private int totalRefundAmount;

    @Schema(description = "순이익 (충전 - 환불)", example = "80000")
    private int netProfit;
}