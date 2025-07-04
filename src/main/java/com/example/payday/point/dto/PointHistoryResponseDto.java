package com.example.payday.point.dto;

import com.example.payday.point.domain.type.PointHistoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointHistoryResponseDto {
    private PointHistoryType type;
    private int currentPoint;
    private LocalDateTime createdAt;
    private String orderId;     // ✅ 주문 ID 추가
    private Long couponId;      // ✅ 쿠폰 ID 추가
    private int pointAmount;
}