package com.example.payday.payment.discount;


public class ErrorEventDiscountPolicy implements DiscountPolicy {

    @Override
    public int calculateDiscount(int originalAmount) {
        return (int)(originalAmount * 0.9);
    }
}