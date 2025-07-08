package com.example.payday.global.exception;

import com.example.payday.global.exception.base.BusinessException;
import com.example.payday.global.exception.base.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    // 비즈니스 예외 처리 (커스텀 예외)
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException ex) {
        log.warn("Business 예외 발생: {}", ex.getMessage());
        return ResponseEntity
                .status(ex.getErrorCode().getStatus())
                .body(new ErrorResponse(
                        ex.getErrorCode().getMessage(),
                        ex.getErrorCode().name(),
                        ex.getErrorCode().getStatus().value()
                ));
    }

    // 서버 내부 예외 처리
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

    // 유효성 검증 실패 처리
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException ex) {
        String errorMessage = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .findFirst()
                .orElse("유효성 검사 실패");

        ErrorCode code = ErrorCode.INVALID_REQUEST;

        return ResponseEntity
                .status(code.getStatus())
                .body(new ErrorResponse(
                        errorMessage,
                        code.name(),
                        code.getStatus().value()
                ));
    }
}
