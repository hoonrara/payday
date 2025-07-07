package com.example.payday.admin.point.controller;

import com.example.payday.admin.point.dto.AdminPointHistoryDto;
import com.example.payday.admin.point.dto.AdminPointHistorySummaryDto;
import com.example.payday.admin.point.dto.AdminPointMonthlyTotalSummaryDto;
import com.example.payday.admin.point.service.AdminPointService;
import com.example.payday.global.dto.PagedResponse;
import com.example.payday.global.mapper.PagedResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "B08. [ADMIN] 어드민 포인트 API", description = "관리자 - 포인트 관리 API")
@RestController
@RequestMapping("/admin/points")
@RequiredArgsConstructor
public class AdminPointController {

    private final AdminPointService adminPointService;

    @Operation(
            summary = "특정 유저의 포인트 요약 및 내역 조회",
            description = "관리자 권한으로 특정 회원의 포인트 충전/환불 요약 정보와 함께 내역 리스트를 페이지네이션으로 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "정상 응답")
    @GetMapping("/{userId}")
    public ResponseEntity<AdminPointHistorySummaryDto> getUserPointHistory(
            @Parameter(description = "조회할 유저 ID", example = "1")
            @PathVariable Long userId,
            @Parameter(hidden = true) Pageable pageable
    ) {
        return ResponseEntity.ok(adminPointService.getAdminHistorySummary(userId, pageable));
    }

    @Operation(
            summary = "전체 포인트 내역 조회",
            description = "관리자 권한으로 전체 회원의 포인트 내역을 페이지네이션으로 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "정상 응답")
    @GetMapping("/histories")
    public ResponseEntity<PagedResponse<AdminPointHistoryDto>> getAllPointHistories(
            @Parameter(hidden = true) Pageable pageable
    ) {
        return ResponseEntity.ok(PagedResponseMapper.toResponse(adminPointService.getAllHistories(pageable)));
    }

    @Operation(
            summary = "월간 총 충전/환불/순이익 통계 조회",
            description = "월간 기준으로 전체 포인트 충전액, 환불액, 순이익을 관리자 권한으로 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "정상 응답")
    @GetMapping("/monthly-summary/total")
    public ResponseEntity<AdminPointMonthlyTotalSummaryDto> getMonthlyTotalSummary() {
        return ResponseEntity.ok(adminPointService.getMonthlyTotalSummary());
    }
}