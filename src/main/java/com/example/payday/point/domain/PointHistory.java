package com.example.payday.point.domain;


import com.example.payday.coupon.domain.Coupon;
import com.example.payday.point.domain.type.PointHistoryType;
import com.example.payday.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int pointAmount; // 충전 또는 환불된 포인트

    @Enumerated(EnumType.STRING)
    private PointHistoryType type;

    @Column(unique = true)
    private String orderId;

    private LocalDateTime createdAt;

    private int paidAmount; // 실제 결제한 금액 (포인트 = 결제금액 + 쿠폰 할인)

    @Column(nullable = false)
    private int remainPoint = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Builder
    public PointHistory(int pointAmount, PointHistoryType type, String orderId, User user, int paidAmount, int remainPoint, Coupon coupon) {
        this.pointAmount = pointAmount;
        this.type = type;
        this.user = user;
        this.paidAmount = paidAmount;
        this.orderId = orderId;
        this.remainPoint = remainPoint;
        this.coupon = coupon;
        this.createdAt = LocalDateTime.now();
    }
}
