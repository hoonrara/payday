package com.example.payday.point.dto;

import com.example.payday.point.domain.type.PointHistoryType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PointHistoryResponseDto {
    private PointHistoryType type;
    private int currentPoint;
    private LocalDateTime createdAt;
    private String orderId;     // ✅ 주문 ID 추가
    private Long couponId;      // ✅ 쿠폰 ID 추가
    private int pointAmount;
}