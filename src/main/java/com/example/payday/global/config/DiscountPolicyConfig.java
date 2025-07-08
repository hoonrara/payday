package com.example.payday.global.config;

import com.example.payday.payment.discount.DefaultDiscountPolicy;
import com.example.payday.payment.discount.DiscountPolicy;
import com.example.payday.payment.discount.ErrorEventDiscountPolicy;
import com.example.payday.payment.discount.HolidayDiscountPolicy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class DiscountPolicyConfig {

    // 기본 할인 정책 (기본 프로필)
    @Bean
    @Profile("default")
    public DiscountPolicy defaultPolicy() {
        return new DefaultDiscountPolicy();
    }

    // 공휴일 할인 정책 (holiday 프로필)
    @Bean
    @Profile("holiday")
    public DiscountPolicy holidayPolicy() {
        return new HolidayDiscountPolicy();
    }

    // 장애 보상 할인 정책 (error-event 프로필)
    @Bean
    @Profile("error-event")
    public DiscountPolicy errorEventPolicy() {
        return new ErrorEventDiscountPolicy();
    }
}
