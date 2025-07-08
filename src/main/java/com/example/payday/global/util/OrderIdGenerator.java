package com.example.payday.global.util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class OrderIdGenerator {

    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");

    /**
     * 주문 ID 자동 생성: ORDER_날짜_시간_유저ID
     */
    public static String generate(Long userId) {
        String timestamp = LocalDateTime.now().format(FORMATTER);
        return "ORDER_" + timestamp + "_" + userId;
    }


}
