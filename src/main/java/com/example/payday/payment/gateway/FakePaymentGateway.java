package com.example.payday.payment.gateway;

import com.example.payday.payment.dto.PaymentRequestDto;
import com.example.payday.payment.dto.PaymentResultDto;
import com.example.payday.payment.type.PaymentMethod;
import com.example.payday.payment.type.PaymentStatus;
import org.springframework.stereotype.Service;

@Service("fake")
public class FakePaymentGateway implements PaymentGateway {

    @Override
    public PaymentResultDto pay(PaymentRequestDto request) {
        // 단순히 요청을 받아 항상 성공으로 처리
        return PaymentResultDto.builder()
                .orderId(request.getOrderId())
                .paymentKey(request.getPaymentKey())
                .method(PaymentMethod.FAKE)
                .amount(request.getAmount())
                .status(PaymentStatus.DONE)
                .build();
    }
}
