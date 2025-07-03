package com.example.payday.coupon.domain;

import com.example.payday.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Coupon {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Enumerated(EnumType.STRING)
    private CouponType type;

    private int amount;  // 1000원 or 10(%)

    private boolean used;

    private LocalDateTime expiredAt;

    private int minOrderAmount;

    private Integer maxDiscountAmount; // ✅ 최대 할인 금액 추가

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "template_id")
    private CouponTemplate template;

    @Builder
    public Coupon(String name, CouponType type, int amount, LocalDateTime expiredAt, int minOrderAmount, Integer maxDiscountAmount, User user, CouponTemplate template) {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.expiredAt = expiredAt;
        this.minOrderAmount = minOrderAmount;
        this.maxDiscountAmount = maxDiscountAmount;
        this.user = user;
        this.template = template;
        this.used = false;
    }

    public boolean isValid() {
        return !used && expiredAt.isAfter(LocalDateTime.now());
    }

    public void markUsed() {
        this.used = true;
    }
}


