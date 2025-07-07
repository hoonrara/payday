package com.example.payday.admin.point.dto;

import com.example.payday.point.domain.type.PointHistoryType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@Schema(description = "관리자 - 포인트 히스토리 응답 DTO")
public class AdminPointHistoryDto {

    @Schema(description = "주문 ID", example = "ORDER_20250705_145425_1")
    private String orderId;

    @Schema(description = "유저 ID", example = "1")
    private Long userId;

    @Schema(description = "유저 이메일", example = "test@example.com")
    private String email;

    @Schema(description = "포인트 금액", example = "10000")
    private int pointAmount;

    @Schema(description = "포인트 타입 (CHARGE, REFUND 등)", example = "CHARGE")
    private PointHistoryType type;

    @Schema(description = "생성일시", example = "2025-07-05T14:54:25.377")
    private LocalDateTime createdAt;
}