package com.example.payday.user.dto;

import lombok.Getter;

@Getter
public class UserSignupRequestDto {
    private String email;
    private String password;
    private String nickname;
}
