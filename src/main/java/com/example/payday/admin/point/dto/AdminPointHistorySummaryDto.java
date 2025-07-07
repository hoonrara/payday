package com.example.payday.admin.point.dto;

import com.example.payday.point.dto.PointHistoryResponseDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

@Getter
@Builder
@AllArgsConstructor
@Schema(description = "관리자 - 특정 유저의 포인트 요약 및 히스토리 목록 DTO")
public class AdminPointHistorySummaryDto {

    @Schema(description = "유저 ID", example = "1")
    private Long userId;

    @Schema(description = "유저 이메일", example = "test@example.com")
    private String email;

    @Schema(description = "총 충전 금액", example = "30000")
    private int totalChargeAmount;

    @Schema(description = "총 환불 금액", example = "10000")
    private int totalRefundAmount;

    @Schema(description = "포인트 히스토리 목록 (페이지네이션)", implementation = PointHistoryResponseDto.class)
    private Page<PointHistoryResponseDto> histories;
}