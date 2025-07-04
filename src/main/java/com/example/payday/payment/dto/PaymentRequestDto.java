package com.example.payday.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto {
    private String paymentKey;
    private String orderId;
    private int amount;
    private String method; // "toss", "kakao" 등 선택
}
