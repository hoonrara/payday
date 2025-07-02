package com.example.payday.payment.controller;

import com.example.payday.payment.dto.PaymentRequestDto;
import com.example.payday.payment.service.PaymentService;
import com.example.payday.point.dto.PointChargeRequestDto;
import com.example.payday.point.dto.RefundRequestDto;
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
    public ResponseEntity<Void> charge(@RequestBody PointChargeRequestDto request) {
        paymentService.chargePoint(request);
        return ResponseEntity.ok().build();
    }

    // 결제 환불 및 포인트 차감
    @PostMapping("/refund")
    public ResponseEntity<Void> refund(@RequestBody RefundRequestDto request) {
        paymentService.refundPoint(request);
        return ResponseEntity.ok().build();
    }
}