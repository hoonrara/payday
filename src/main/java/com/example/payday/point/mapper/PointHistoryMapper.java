package com.example.payday.point.mapper;

import com.example.payday.coupon.domain.Coupon;
import com.example.payday.point.domain.PointHistory;
import com.example.payday.point.domain.type.PointHistoryType;
import com.example.payday.point.dto.PointHistoryResponseDto;
import com.example.payday.user.domain.User;

public class PointHistoryMapper {

    public static PointHistoryResponseDto toDto(PointHistory entity) {
        return PointHistoryResponseDto.builder()
                .amount(entity.getAmount())
                .type(entity.getType())
                .currentPoint(entity.getCurrentPoint())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static PointHistory toChargeHistory(User user, int amount, String orderId, Coupon coupon) {
        return PointHistory.builder()
                .user(user)
                .amount(amount)
                .type(PointHistoryType.CHARGE)
                .orderId(orderId)
                .currentPoint(user.getPoint())
                .coupon(coupon)
                .build();
    }

    public static PointHistory toRefundHistory(User user, int amount, String orderId) {
        return PointHistory.builder()
                .user(user)
                .amount(amount)
                .type(PointHistoryType.REFUND)
                .orderId(orderId)
                .currentPoint(user.getPoint())
                .build();
    }
}
