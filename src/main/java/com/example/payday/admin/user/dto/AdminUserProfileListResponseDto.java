package com.example.payday.admin.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "관리자 - 회원 프로필 정보 DTO")
public class AdminUserProfileListResponseDto {

    @Schema(description = "유저 ID", example = "1")
    private Long userId;

    @Schema(description = "이메일", example = "user@example.com")
    private String email;

    @Schema(description = "닉네임", example = "hoon")
    private String nickname;

    @Schema(description = "포인트 잔액", example = "30000")
    private int point;

    @Schema(description = "프로필 조회 수", example = "15")
    private int viewCount;

    @Schema(description = "가입 일시", example = "2025-07-01T14:23:11")
    private LocalDateTime joinedAt;

    @Schema(description = "마지막 로그인 일시", example = "2025-07-06T21:10:00", nullable = true)
    private String lastLoginAt;

    @Schema(description = "정지 상태 (예: 정지됨 / 정상)", example = "정상", nullable = true)
    private String suspendedStatus;

    @Schema(description = "누적 신고 건수", example = "신고 2회", nullable = true)
    private String reportCountText;
}