package com.example.payday.point.exception;

public class InsufficientPointException extends RuntimeException {
    public InsufficientPointException() {
        super("포인트가 부족합니다.");
    }

    public InsufficientPointException(String message) {
        super(message);
    }
}
