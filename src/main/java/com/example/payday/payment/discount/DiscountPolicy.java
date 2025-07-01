package com.example.payday.payment.discount;

public interface DiscountPolicy {
    int calculateDiscount(int originalAmount);
}
