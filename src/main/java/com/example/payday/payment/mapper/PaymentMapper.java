package com.example.payday.payment.mapper;

import com.example.payday.payment.dto.PaymentRequestDto;
import com.example.payday.point.dto.PointChargeRequestDto;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PaymentMapper {

    public static PaymentRequestDto toPaymentRequest(PointChargeRequestDto request, int finalAmount, String orderId) {
        return PaymentRequestDto.builder()
                .paymentKey(request.getPaymentKey())
                .orderId(orderId)
                .amount(finalAmount)
                .method(request.getMethod())
                .build();
    }
}