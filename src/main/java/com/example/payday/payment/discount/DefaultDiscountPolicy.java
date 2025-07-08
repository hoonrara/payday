package com.example.payday.payment.discount;



public class DefaultDiscountPolicy implements DiscountPolicy {

    @Override
    public int calculateDiscount(int originalAmount) {
        // 할인 없음
        return originalAmount;
    }
}