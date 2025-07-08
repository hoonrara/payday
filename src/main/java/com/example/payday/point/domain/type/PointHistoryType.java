package com.example.payday.point.domain.type;

import com.fasterxml.jackson.annotation.JsonValue;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "포인트 히스토리 타입 (충전, 사용, 환불)")
public enum PointHistoryType {

    @Schema(description = "포인트 충전")
    CHARGE("충전"),

    @Schema(description = "포인트 사용")
    USE("사용"),

    @Schema(description = "포인트 환불")
    REFUND("환불");

    private final String description;

    PointHistoryType(String description) {
        this.description = description;
    }

    @JsonValue
    public String getDescription() {
        return description;
    }
}
