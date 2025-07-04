package com.example.payday.point.dto;

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
public class RefundRequestDto {

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "주문 ID는 필수입니다.")
    private String orderId;
}