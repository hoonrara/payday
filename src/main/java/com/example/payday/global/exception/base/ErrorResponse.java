package com.example.payday.global.exception.base;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Schema(description = "에러 응답 객체 예시")
public class ErrorResponse {

  @Schema(description = "에러 메시지 (예: 존재하지 않는 사용자입니다.)", example = "존재하지 않는 사용자입니다.")
  private final String message;

  @Schema(description = "에러 코드 (예: USER_NOT_FOUND)", example = "USER_NOT_FOUND")
  private final String code;

  @Schema(description = "HTTP 상태 코드 (예: 404)", example = "404")
  private final int status;
}