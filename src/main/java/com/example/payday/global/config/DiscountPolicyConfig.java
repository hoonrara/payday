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

    @Bean
    @Profile("default")
    public DiscountPolicy defaultPolicy() {
        return new DefaultDiscountPolicy();
    }

    @Bean
    @Profile("holiday")
    public DiscountPolicy holidayPolicy() {
        return new HolidayDiscountPolicy();
    }

    @Bean
    @Profile("error-event")
    public DiscountPolicy errorEventPolicy() {
        return new ErrorEventDiscountPolicy();
    }
}
