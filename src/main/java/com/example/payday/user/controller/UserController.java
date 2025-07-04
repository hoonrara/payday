package com.example.payday.user.controller;

import com.example.payday.user.dto.UserSignupRequestDto;
import com.example.payday.user.dto.UserSignupResponseDto;
import com.example.payday.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<UserSignupResponseDto> signup(@RequestBody @Validated UserSignupRequestDto dto) {
        UserSignupResponseDto response = userService.signup(dto);
        return ResponseEntity.ok(response);
    }
}