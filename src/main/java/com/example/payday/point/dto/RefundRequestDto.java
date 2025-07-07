package com.example.payday.point.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "포인트 환불 요청 DTO")
public class RefundRequestDto {

    @Schema(description = "유저 ID", example = "1")
    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    @Schema(description = "주문 ID", example = "ORDER_20250705_145425_1")
    @NotBlank(message = "주문 ID는 필수입니다.")
    private String orderId;
}