package com.example.payday.admin.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AdminUserProfileListResponseDto {

    private Long userId;
    private String email;
    private String nickname;
    private int point;
    private int viewCount;
    private LocalDateTime joinedAt;

    // 추후 확장용
    private String lastLoginAt;
    private String suspendedStatus;
    private String reportCountText;
}
