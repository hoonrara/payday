package com.example.payday.payment.gateway;

import com.example.payday.payment.dto.PaymentRequestDto;
import com.example.payday.payment.dto.PaymentResultDto;
import com.example.payday.payment.type.PaymentStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service("fake")
public class FakePaymentGateway implements PaymentGateway {

    @Override
    public PaymentResultDto pay(PaymentRequestDto request) {
        // 테스트용 Gateway - 승인 처리 Mock
        return PaymentResultDto.builder()
                .orderId(request.getOrderId())
                .paymentKey(request.getPaymentKey())
                .amount(request.getAmount())
                .status(PaymentStatus.DONE)
                .approvedAt(LocalDateTime.now())
                .build();
    }
}