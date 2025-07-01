package com.example.payday.point.dto;


import com.example.payday.payment.type.PaymentMethod;
import lombok.Getter;

@Getter
public class PointChargeRequestDto {
    private Long userId;
    private String paymentKey;
    private String orderId;
    private int amount;
    private String method;}
