package com.example.payday.global.exception.base;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class ErrorResponse {
  private final String message;
  private final String code;
  private final int status;
}