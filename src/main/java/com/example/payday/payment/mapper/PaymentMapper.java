package com.example.payday.payment.mapper;

import com.example.payday.payment.dto.PaymentRequestDto;
import com.example.payday.point.dto.PointChargeRequestDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {

    public PaymentRequestDto toPaymentRequest(PointChargeRequestDto request, int finalAmount) {
        return new PaymentRequestDto(
                request.getPaymentKey(),
                request.getOrderId(),
                finalAmount,
                request.getMethod()
        );
    }
}