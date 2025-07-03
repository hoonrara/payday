package com.example.payday.point.exception;

import com.example.payday.global.exception.ErrorCode;
import com.example.payday.global.exception.base.BusinessException;

public class DuplicateOrderIdException extends BusinessException {
    public DuplicateOrderIdException() {
        super(ErrorCode.DUPLICATE_ORDER_ID);
    }
}