package com.example.payday.global.exception;

import com.example.payday.global.exception.base.BusinessException;
import com.example.payday.global.exception.base.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        log.warn("Business 예외 발생: {}", ex.getMessage());
        return ResponseEntity
                .status(ex.getErrorCode().getStatus())
                .body(new ErrorResponse(
                        ex.getErrorCode().getMessage(),
                        ex.getErrorCode().name(),  // "INVALID_PAYMENT"
                        ex.getErrorCode().getStatus().value()
                ));
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<ErrorResponse> handleRuntimeException(RuntimeException ex) {
        log.error("서버 오류 발생", ex);
        ErrorCode code = ErrorCode.UNKNOWN_ERROR;
        return ResponseEntity
                .status(code.getStatus())
                .body(new ErrorResponse(
                        code.getMessage(),
                        code.name(),
                        code.getStatus().value()
                ));
    }
}
