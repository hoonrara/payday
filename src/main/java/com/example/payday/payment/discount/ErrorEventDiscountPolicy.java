package com.example.payday.payment.discount;


public class ErrorEventDiscountPolicy implements DiscountPolicy {

    @Override
    public int calculateDiscount(int originalAmount) {
        // 장애 보상 10% 할인
        return (int)(originalAmount * 0.9);
    }
}