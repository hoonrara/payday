package com.example.payday.coupon.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CouponTemplate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CouponType type;

    private int amount;
    private Integer maxDiscountAmount;

    private int minOrderAmount;

    private LocalDateTime expiredAt;

    @Builder
    public CouponTemplate(String name, CouponType type, int amount,
                          Integer maxDiscountAmount, int minOrderAmount, LocalDateTime expiredAt) {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.maxDiscountAmount = maxDiscountAmount;
        this.minOrderAmount = minOrderAmount;
        this.expiredAt = expiredAt;
    }
}
