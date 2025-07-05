package com.example.payday.admin.point.dto;

import com.example.payday.point.dto.PointHistoryResponseDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.domain.Page;

import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class AdminPointHistorySummaryDto {
    private Long userId;
    private String email;
    private int totalChargeAmount;
    private int totalRefundAmount;
    private Page<PointHistoryResponseDto> histories;}