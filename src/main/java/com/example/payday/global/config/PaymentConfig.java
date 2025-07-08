package com.example.payday.global.config;

import com.example.payday.payment.gateway.PaymentGateway;
import com.example.payday.payment.type.PaymentMethod;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.EnumMap;
import java.util.Map;

@Configuration
@RequiredArgsConstructor
public class PaymentConfig {

    private final PaymentGateway toss;
    private final PaymentGateway fake;

    /**
     * 결제 수단에 따른 Gateway 매핑 구성
     */
    @Bean
    public Map<PaymentMethod, PaymentGateway> gatewayMap() {
        Map<PaymentMethod, PaymentGateway> map = new EnumMap<>(PaymentMethod.class);
        map.put(PaymentMethod.TOSS, toss);
        map.put(PaymentMethod.FAKE, fake);
        return map;
    }
}