package com.example.payday.admin.point.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminPointMonthlyTotalSummaryDto {
    private int totalChargeAmount;
    private int totalRefundAmount;
    private int netProfit;
}
