package com.example.payday.point.dto;

import com.example.payday.point.domain.type.PointHistoryType;
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
@Schema(description = "포인트 내역 응답 DTO")
public class PointHistoryResponseDto {

    @Schema(description = "포인트 이력 타입 (CHARGE, REFUND)", example = "CHARGE")
    private PointHistoryType type;

    @Schema(description = "남은 포인트", example = "15000")
    private int remainPoint;

    @Schema(description = "이력 생성 시간", example = "2025-07-05T14:22:00")
    private LocalDateTime createdAt;

    @Schema(description = "주문 ID", example = "ORDER_20250705_145425_1")
    private String orderId;

    @Schema(description = "쿠폰 ID", example = "3")
    private Long couponId;

    @Schema(description = "변동된 포인트 양", example = "10000")
    private int pointAmount;
}