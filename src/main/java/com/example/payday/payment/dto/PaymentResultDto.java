package com.example.payday.payment.dto;

import com.example.payday.payment.type.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResultDto {
    private String paymentKey;
    private String orderId;
    private int amount;
    private PaymentStatus status;
    private LocalDateTime approvedAt;
}