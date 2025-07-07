package com.example.payday.user.controller;

import com.example.payday.global.dto.PagedResponse;
import com.example.payday.global.mapper.PagedResponseMapper;
import com.example.payday.user.dto.UserProfileDetailResponseDto;
import com.example.payday.user.dto.UserProfileListResponseDto;
import com.example.payday.user.exception.MissingUserIdForMeEndpointException;
import com.example.payday.user.service.UserProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profiles")
@Tag(name = "A02. [USER] 프로필 API", description = "회원 프로필 조회 관련 API")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @Operation(summary = "프로필 목록 조회", description = "회원 프로필을 정렬 기준에 따라 페이지네이션으로 조회합니다.")
    @ApiResponse(responseCode = "200", description = "정상 응답")
    @GetMapping
    public ResponseEntity<PagedResponse<UserProfileListResponseDto>> getProfiles(
            @RequestParam(defaultValue = "date") String sortKey,
            Pageable pageable
    ) {
        Page<UserProfileListResponseDto> profiles = userProfileService.getAllProfiles(sortKey, pageable);
        return ResponseEntity.ok(PagedResponseMapper.toResponse(profiles));
    }

    @Operation(summary = "특정 유저 프로필 조회", description = "userId에 해당하는 회원의 상세 프로필을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "정상 응답")
    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileDetailResponseDto> getProfileDetail(@PathVariable Long userId) {
        return ResponseEntity.ok(userProfileService.getProfileDetail(userId));
    }

    @Operation(summary = "내 프로필 조회", description = "파라미터로 받은 userId를 기반으로 자신의 프로필을 조회합니다.")
    @ApiResponse(responseCode = "200", description = "정상 응답")
    @GetMapping("/me")
    public ResponseEntity<UserProfileDetailResponseDto> getMyProfile(@RequestParam(required = false) Long userId) {
        if (userId == null) {
            throw new MissingUserIdForMeEndpointException();
        }
        return ResponseEntity.ok(userProfileService.getMyProfile(userId));
    }
}