package com.example.payday.admin.user.controller;

import com.example.payday.admin.user.dto.AdminUserProfileListResponseDto;
import com.example.payday.admin.user.service.AdminUserProfileService;
import com.example.payday.global.dto.PagedResponse;
import com.example.payday.global.mapper.PagedResponseMapper;
import com.example.payday.global.exception.base.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "B07. [ADMIN] 어드민 사용자 프로필 API", description = "관리자 - 사용자 프로필 관리 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/profiles")
public class AdminUserProfileController {

    private final AdminUserProfileService adminUserProfileService;

    @Operation(summary = "전체 회원 프로필 목록 조회", description = "관리자 권한으로 전체 회원의 프로필 정보를 정렬조건 및 페이징으로 조회합니다.")
    @ApiResponse(responseCode = "200", description = "정상 응답")
    @GetMapping
    public ResponseEntity<PagedResponse<AdminUserProfileListResponseDto>> getAllProfiles(
            @Parameter(description = "정렬 기준 (name, view, date)", example = "date")
            @RequestParam(defaultValue = "date") String sortKey,
            @Parameter(hidden = true) Pageable pageable) {
        Page<AdminUserProfileListResponseDto> page = adminUserProfileService.getAllProfiles(sortKey, pageable);
        return ResponseEntity.ok(PagedResponseMapper.toResponse(page));
    }

    @Operation(summary = "특정 회원 프로필 단건 조회", description = "관리자 권한으로 특정 유저의 프로필 상세 정보를 조회합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "정상 응답"),
            @ApiResponse(responseCode = "404", description = "존재하지 않는 사용자",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @GetMapping("/{userId}")
    public ResponseEntity<AdminUserProfileListResponseDto> getUserProfile(
            @Parameter(description = "회원 ID", example = "1")
            @PathVariable Long userId) {
        return ResponseEntity.ok(adminUserProfileService.getUserProfileById(userId));
    }
}
