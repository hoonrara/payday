package com.example.payday.payment.controller;

import com.example.payday.payment.dto.PaymentResultDto;
import com.example.payday.payment.service.PaymentService;
import com.example.payday.point.dto.PointChargeRequestDto;
import com.example.payday.point.dto.RefundRequestDto;
import com.example.payday.point.dto.RefundResultDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    // 결제 수단(Toss 등)으로 충전
    @PostMapping("/charge")
    public ResponseEntity<PaymentResultDto> charge(@RequestBody PointChargeRequestDto request) {
        PaymentResultDto result = paymentService.chargePoint(request);
        return ResponseEntity.ok(result);
    }

    // 결제 환불 및 포인트 차감
    @PostMapping("/refund")
    public ResponseEntity<RefundResultDto> refund(@RequestBody RefundRequestDto request) {
        RefundResultDto result = paymentService.refundPoint(request);
        return ResponseEntity.ok(result);
    }
}