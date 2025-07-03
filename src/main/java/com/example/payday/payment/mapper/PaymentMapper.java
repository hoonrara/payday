package com.example.payday.payment.mapper;

import com.example.payday.payment.dto.PaymentRequestDto;
import com.example.payday.point.dto.PointChargeRequestDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentRequestDto toPaymentRequest(PointChargeRequestDto request, int finalAmount, String orderId) {
        return new PaymentRequestDto(
                request.getPaymentKey(),
                orderId,
                finalAmount,
                request.getMethod()
        );
    }
}