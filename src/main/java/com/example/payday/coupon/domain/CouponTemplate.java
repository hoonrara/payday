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

    // ✅ [추가] 자동 발급 조건
    private Integer viewThreshold;      // 조회수 조건 (nullable)

    // ✅ [추가] 선착순 조건
    private Integer maxIssueCount;      // 발급 제한 수 (nullable)
    private int issuedCount;            // 현재 발급 수

    // ✅ [추가] 자동 발급 여부
    private boolean autoIssue;

    @Builder
    public CouponTemplate(String name, CouponType type, int amount,
                          Integer maxDiscountAmount, int minOrderAmount,
                          LocalDateTime expiredAt, Integer viewThreshold,
                          Integer maxIssueCount, int issuedCount, boolean autoIssue) {
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.maxDiscountAmount = maxDiscountAmount;
        this.minOrderAmount = minOrderAmount;
        this.expiredAt = expiredAt;
        this.viewThreshold = viewThreshold;
        this.maxIssueCount = maxIssueCount;
        this.issuedCount = issuedCount;
        this.autoIssue = autoIssue;
    }

    public void increaseIssuedCount() {
        this.issuedCount++;
    }
}