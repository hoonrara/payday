package com.example.payday.point.mapper;

import com.example.payday.point.domain.PointHistory;
import com.example.payday.point.dto.PointHistoryResponseDto;

public class PointHistoryMapper {

    public static PointHistoryResponseDto toDto(PointHistory entity) {
        return PointHistoryResponseDto.builder()
                .amount(entity.getAmount())
                .type(entity.getType())
                .currentPoint(entity.getCurrentPoint())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}
