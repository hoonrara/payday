package com.example.payday.point.exception;

import com.example.payday.global.exception.ErrorCode;
import com.example.payday.global.exception.base.BusinessException;

public class PointHistoryNotFoundException extends BusinessException {
    public PointHistoryNotFoundException() {
        super(ErrorCode.HISTORY_NOT_FOUND);
    }
}