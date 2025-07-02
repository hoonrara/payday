package com.example.payday.user.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class UserProfileDetailResponseDto {

    private String nickname;
    private int viewCount;
    private LocalDateTime createdAt;
}