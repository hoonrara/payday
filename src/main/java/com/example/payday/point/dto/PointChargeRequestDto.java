package com.example.payday.point.dto;


import com.example.payday.payment.type.PaymentMethod;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PointChargeRequestDto {
    private Long userId;
    private String paymentKey;
    private String orderId;
    private int pointAmount;
    private String method;
    private Long couponId;
}
