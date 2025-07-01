package com.example.payday.payment.gateway;

import com.example.payday.payment.dto.PaymentRequestDto;
import com.example.payday.payment.dto.PaymentResultDto;

public interface PaymentGateway {
    PaymentResultDto pay(PaymentRequestDto request);
}
