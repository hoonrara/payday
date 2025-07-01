package com.example.payday.payment.gateway;

import com.example.payday.global.exception.ErrorCode;
import com.example.payday.payment.dto.PaymentRequestDto;
import com.example.payday.payment.dto.PaymentResultDto;
import com.example.payday.payment.exception.InvalidPaymentException;
import com.example.payday.payment.type.PaymentMethod;
import com.example.payday.payment.type.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Base64;
import java.util.Map;

@Slf4j
@Service("toss")
@RequiredArgsConstructor
public class TossPaymentGateway implements PaymentGateway {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${toss.secret-key}")
    private String secretKey;

    private static final String TOSS_API_URL = "https://api.tosspayments.com/v1/payments/confirm";

    @Override
    public PaymentResultDto pay(PaymentRequestDto request) {
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String basicAuth = Base64.getEncoder().encodeToString((secretKey + ":").getBytes());
        headers.set("Authorization", "Basic " + basicAuth);

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(
                Map.of(
                        "paymentKey", request.getPaymentKey(),
                        "orderId", request.getOrderId(),
                        "amount", request.getAmount()
                ),
                headers
        );

        try {
            ResponseEntity<Map> response = restTemplate.postForEntity(TOSS_API_URL, entity, Map.class);
            Map<String, Object> body = response.getBody();

            if (response.getStatusCode() == HttpStatus.OK && body != null) {
                return PaymentResultDto.builder()
                        .orderId((String) body.get("orderId"))
                        .paymentKey((String) body.get("paymentKey"))
                        .method(PaymentMethod.TOSS)
                        .amount((Integer) body.get("totalAmount"))
                        .status(PaymentStatus.DONE)
                        .build();
            } else {
                throw new InvalidPaymentException();
            }
        } catch (Exception e) {
            log.error("Toss 결제 오류", e);
            throw new InvalidPaymentException();
        }
    }
}
