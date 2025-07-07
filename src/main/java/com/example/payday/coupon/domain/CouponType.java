package com.example.payday.coupon.domain;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "쿠폰 할인 타입 (정액/정률)")
public enum CouponType {

    @Schema(description = "정액 할인")
    FIXED("정액 할인"),

    @Schema(description = "정률 할인")
    PERCENTAGE("정률 할인");

    private final String description;

    CouponType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
