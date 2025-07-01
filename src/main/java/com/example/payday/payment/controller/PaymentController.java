package com.example.payday.payment.controller;

import com.example.payday.payment.service.PaymentService;
import com.example.payday.point.dto.PointChargeRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping("/charge")
    public ResponseEntity<Void> charge(@RequestBody PointChargeRequestDto request) {
        paymentService.chargePointWithPayment(request);
        return ResponseEntity.ok().build();
    }
}