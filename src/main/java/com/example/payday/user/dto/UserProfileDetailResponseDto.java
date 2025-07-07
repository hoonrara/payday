package com.example.payday.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "회원 프로필 상세 조회 DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserProfileDetailResponseDto {

    @Schema(description = "닉네임", example = "멋쟁이개발자")
    private String nickname;

    @Schema(description = "조회수", example = "50")
    private int viewCount;

    @Schema(description = "가입일", example = "2025-07-06T10:20:30")
    private LocalDateTime createdAt;
}