package com.example.payday.user.controller;

import com.example.payday.user.dto.UserSignupRequestDto;
import com.example.payday.user.dto.UserSignupResponseDto;
import com.example.payday.user.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import com.example.payday.global.exception.base.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
@Tag(name = "A01. [USER] 사용자 API", description = "회원가입 등 사용자 관련 API")
public class UserController {

    private final UserService userService;

    @Operation(summary = "회원가입", description = "이메일, 비밀번호, 닉네임을 통해 회원가입을 진행합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "회원가입 성공"),
            @ApiResponse(responseCode = "400", description = "중복 이메일 또는 입력값 검증 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponseDto> signup(@RequestBody @Validated UserSignupRequestDto dto) {
        UserSignupResponseDto response = userService.signup(dto);
        return ResponseEntity.ok(response);
    }
}