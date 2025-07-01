package com.example.payday.payment.mapper;

import com.example.payday.payment.dto.PaymentRequestDto;
import com.example.payday.point.dto.PointChargeRequestDto;
import org.springframework.stereotype.Component;

@Component
public class PaymentMapper {
    public PaymentRequestDto toPaymentRequest(PointChargeRequestDto request) {
        return new PaymentRequestDto(
                request.getPaymentKey(),
                request.getOrderId(),
                request.getAmount(),
                request.getMethod()
        );
    }
}
