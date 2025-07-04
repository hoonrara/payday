package com.example.payday.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDetailResponseDto {

    private String nickname;
    private int viewCount;
    private LocalDateTime createdAt;
}