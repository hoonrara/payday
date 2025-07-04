package com.example.payday.point.dto;


import com.example.payday.payment.type.PaymentMethod;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointChargeRequestDto {

    @NotNull(message = "유저 ID는 필수입니다.")
    private Long userId;

    @NotBlank(message = "결제 키는 필수입니다.")
    private String paymentKey;

//    @NotBlank(message = "주문 ID는 필수입니다.")
//    private String orderId;

    @Positive(message = "충전 포인트는 0보다 커야 합니다.")
    private int pointAmount;

    @NotBlank(message = "결제 수단은 필수입니다.")
    private String method;

    private Long couponId; // optional
}
