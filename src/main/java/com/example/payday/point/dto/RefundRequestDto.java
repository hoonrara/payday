package com.example.payday.point.dto;

import lombok.Getter;

@Getter
public class RefundRequestDto {
    private Long userId;
    private String orderId;
}