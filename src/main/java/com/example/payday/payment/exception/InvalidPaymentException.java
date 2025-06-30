package com.example.payday.payment.exception;

public class InvalidPaymentException extends RuntimeException {
    public InvalidPaymentException() {
        super("유효하지 않은 결제 요청입니다.");
    }

    // 사용자 정의 메시지용 생성자 추가
    public InvalidPaymentException(String message) {
        super(message);
    }
}