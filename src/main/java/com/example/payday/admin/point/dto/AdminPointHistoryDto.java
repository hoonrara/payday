package com.example.payday.admin.point.dto;

import com.example.payday.point.domain.type.PointHistoryType;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class AdminPointHistoryDto {
    private String orderId;
    private Long userId;
    private String email;
    private int pointAmount;
    private PointHistoryType type;
    private LocalDateTime createdAt;
}
