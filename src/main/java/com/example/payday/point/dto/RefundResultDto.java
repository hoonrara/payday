package com.example.payday.point.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RefundResultDto {
    private String paymentKey;       // 임의의 키, 여기선 orderId 대체로 사용
    private int cancelAmount;
    private String cancelReason;
    private LocalDateTime canceledAt;
    private String status;           // "CANCELED"
}