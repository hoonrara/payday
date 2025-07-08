package com.example.payday.payment.discount;


public class HolidayDiscountPolicy implements DiscountPolicy {

    @Override
    public int calculateDiscount(int originalAmount) {
        // 20% 할인 적용
        return (int)(originalAmount * 0.8);
    }
}