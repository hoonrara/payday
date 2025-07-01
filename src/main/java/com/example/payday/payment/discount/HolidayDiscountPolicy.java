package com.example.payday.payment.discount;


public class HolidayDiscountPolicy implements DiscountPolicy {

    @Override
    public int calculateDiscount(int originalAmount) {
        return (int)(originalAmount * 0.8);
    }
}
