package com.example.payday.payment.gateway;

import com.example.payday.payment.dto.PaymentRequestDto;
import com.example.payday.payment.dto.PaymentResultDto;
import com.example.payday.payment.exception.InvalidPaymentException;
import com.example.payday.payment.type.PaymentStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
                        .paymentKey((String) body.get("paymentKey"))
                        .orderId((String) body.get("orderId"))
                        .amount((Integer) body.get("amount"))
                        .status(PaymentStatus.DONE)
                        .approvedAt(parseApprovedAt(body.get("approvedAt")))
                        .build();
            } else {
                throw new InvalidPaymentException();
            }
        } catch (Exception e) {
            log.error("Toss 결제 오류", e);
            throw new InvalidPaymentException();
        }
    }

    private LocalDateTime parseApprovedAt(Object approvedAtRaw) {
        try {
            return LocalDateTime.parse((String) approvedAtRaw, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
        } catch (Exception e) {
            log.warn("approvedAt 파싱 실패, 현재 시간으로 대체", e);
            return LocalDateTime.now();
        }
    }
}
