package com.example.payday.user.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Schema(description = "회원가입 응답 DTO")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UserSignupResponseDto {

    @Schema(description = "회원 ID", example = "1")
    private Long userId;

    @Schema(description = "이메일", example = "user@example.com")
    private String email;

    @Schema(description = "닉네임", example = "멋쟁이개발자")
    private String nickname;

    @Schema(description = "응답 메시지", example = "회원가입이 완료되었습니다.")
    private String message;
}