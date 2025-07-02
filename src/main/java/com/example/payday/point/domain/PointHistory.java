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

    private int amount;

    @Enumerated(EnumType.STRING)
    private PointHistoryType type;

    private String orderId;

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private int currentPoint = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "coupon_id")
    private Coupon coupon;

    @Builder
    public PointHistory(int amount, PointHistoryType type,String orderId, User user, int currentPoint, Coupon coupon) {
        this.amount = amount;
        this.type = type;
        this.user = user;
        this.orderId = orderId;
        this.currentPoint = currentPoint;
        this.coupon = coupon;
        this.createdAt = LocalDateTime.now();
    }



}
