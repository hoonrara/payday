package com.example.payday.payment.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class PaymentRequestDto {
    private String paymentKey;
    private String orderId;
    private int amount;
    private String method; // "toss", "kakao" 등 선택
}
