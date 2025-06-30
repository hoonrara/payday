package com.example.payday.point.dto;

import com.example.payday.point.domain.type.PointHistoryType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class PointHistoryResponseDto {
    private int amount;
    private PointHistoryType type;
    private int currentPoint;
    private LocalDateTime createdAt;
}