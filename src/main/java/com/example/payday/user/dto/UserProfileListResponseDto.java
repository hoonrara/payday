package com.example.payday.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "회원 프로필 목록 조회 DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileListResponseDto {

    @Schema(description = "닉네임", example = "멋쟁이개발자")
    private String nickname;

    @Schema(description = "프로필 조회수", example = "12")
    private int viewCount;

    @Schema(description = "프로필 생성일", example = "2025-07-07T09:00:00")
    private LocalDateTime createdAt;
}