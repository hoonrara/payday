package com.example.payday.payment.dto;

import com.example.payday.payment.type.PaymentMethod;
import com.example.payday.payment.type.PaymentStatus;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PaymentResultDto {
    private String orderId;
    private String paymentKey;
    private PaymentMethod method;
    private int amount;
    private PaymentStatus status;
}