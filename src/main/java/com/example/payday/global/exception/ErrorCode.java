package com.example.payday.global.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public enum ErrorCode {
    INVALID_PAYMENT("결제 승인 실패", HttpStatus.BAD_REQUEST),
    USER_NOT_FOUND("존재하지 않는 사용자입니다.", HttpStatus.NOT_FOUND),
    INSUFFICIENT_POINT("포인트가 부족합니다.", HttpStatus.BAD_REQUEST),
    UNKNOWN_ERROR("서버 내부 오류가 발생했습니다.", HttpStatus.INTERNAL_SERVER_ERROR),
    UNSUPPORTED_PAYMENT_METHOD("지원하지 않는 결제 방식입니다.", HttpStatus.BAD_REQUEST),
    COUPON_NOT_FOUND("존재하지 않는 쿠폰입니다.", HttpStatus.NOT_FOUND),
    COUPON_ALREADY_USED("이미 사용된 쿠폰입니다.", HttpStatus.BAD_REQUEST),
    COUPON_EXPIRED("만료된 쿠폰입니다.", HttpStatus.BAD_REQUEST),
    COUPON_MINIMUM_AMOUNT("최소 주문 금액을 충족하지 않습니다.", HttpStatus.BAD_REQUEST),
    UNSUPPORTED_COUPON_TYPE("지원하지 않는 쿠폰 타입입니다.", HttpStatus.BAD_REQUEST),
    HISTORY_NOT_FOUND("해당 주문의 충전 기록이 없습니다.", HttpStatus.NOT_FOUND),
    COUPON_TEMPLATE_NOT_FOUND("존재하지 않는 쿠폰 템플릿입니다.", HttpStatus.NOT_FOUND),
    DUPLICATE_ORDER_ID("이미 사용된 주문 ID입니다.", HttpStatus.BAD_REQUEST),
    INVALID_REQUEST("잘못된 요청입니다.", HttpStatus.BAD_REQUEST),
    ALREADY_ISSUED_COUPON("이미 발급받은 쿠폰입니다.", HttpStatus.BAD_REQUEST),
    MISSING_USER_ID_FOR_ME_ENDPOINT("me 엔드포인트는 인증 미구현 상태이므로 userId 쿼리 파라미터가 필요합니다.", HttpStatus.BAD_REQUEST),
    COUPON_OUT_OF_STOCK("쿠폰이 모두 소진되었습니다.", HttpStatus.BAD_REQUEST),
    


    ;




    private final String message;
    private final HttpStatus status;

    ErrorCode(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
    }
}