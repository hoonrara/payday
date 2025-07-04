package com.example.payday.point.mapper;

import com.example.payday.coupon.domain.Coupon;
import com.example.payday.point.domain.PointHistory;
import com.example.payday.point.domain.type.PointHistoryType;
import com.example.payday.point.dto.PointHistoryResponseDto;
import com.example.payday.user.domain.User;

public class PointHistoryMapper {

    public static PointHistoryResponseDto toDto(PointHistory entity) {
        return PointHistoryResponseDto.builder()
                .pointAmount(entity.getPointAmount())  // ✅ 수정
                .type(entity.getType())
                .remainPoint(entity.getRemainPoint())
                .createdAt(entity.getCreatedAt())
                .orderId(entity.getOrderId())                           // ✅ 추가
                .couponId(entity.getCoupon() != null                   // ✅ 추가
                        ? entity.getCoupon().getId() : null)
                .build();
    }

    public static PointHistory toChargeHistory(User user, int pointAmount, int paidAmount, String orderId, Coupon coupon) {
        return PointHistory.builder()
                .user(user)
                .pointAmount(pointAmount)
                .paidAmount(paidAmount)
                .type(PointHistoryType.CHARGE)
                .orderId(orderId)
                .remainPoint(user.getPoint())
                .coupon(coupon)
                .build();
    }

    public static PointHistory toRefundHistory(User user, int pointAmount, String orderId) {
        return PointHistory.builder()
                .user(user)
                .pointAmount(pointAmount)
                .type(PointHistoryType.REFUND)
                .orderId(orderId)
                .remainPoint(user.getPoint())
                .build();
    }
}
