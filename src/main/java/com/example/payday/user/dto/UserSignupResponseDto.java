package com.example.payday.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupResponseDto {
    private Long userId;
    private String email;
    private String nickname;
    private String message;
}