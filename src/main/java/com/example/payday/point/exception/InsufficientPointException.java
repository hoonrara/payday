package com.example.payday.point.exception;

import com.example.payday.global.exception.ErrorCode;
import com.example.payday.global.exception.base.BusinessException;

public class InsufficientPointException extends BusinessException {
    public InsufficientPointException() {
        super(ErrorCode.INSUFFICIENT_POINT);
    }
}