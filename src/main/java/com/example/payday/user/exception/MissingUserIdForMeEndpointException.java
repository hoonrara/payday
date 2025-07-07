package com.example.payday.user.exception;

import com.example.payday.global.exception.ErrorCode;
import com.example.payday.global.exception.base.BusinessException;

public class MissingUserIdForMeEndpointException extends BusinessException {
    public MissingUserIdForMeEndpointException() {
        super(ErrorCode.MISSING_USER_ID_FOR_ME_ENDPOINT);
    }
}