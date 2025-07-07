package com.example.payday.point.controller;

import com.example.payday.global.dto.PagedResponse;
import com.example.payday.global.mapper.PagedResponseMapper;
import com.example.payday.point.dto.PointHistoryResponseDto;
import com.example.payday.point.service.PointService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequiredArgsConstructor
@RequestMapping("/api/users/{userId}/points")
@Tag(name = "A05. [USER] 포인트 API", description = "포인트 내역 및 관련 기능 제공")
public class PointController {

    private final PointService pointService;

    @Operation(summary = "포인트 내역 조회", description = "사용자의 포인트 충전/환불 내역을 페이지네이션으로 조회합니다.")
    @ApiResponse(responseCode = "200", description = "정상 응답")
    @GetMapping("/histories")
    public ResponseEntity<PagedResponse<PointHistoryResponseDto>> getHistories(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        Page<PointHistoryResponseDto> histories = pointService.getPointHistoriesByUser(userId, pageable);
        return ResponseEntity.ok(PagedResponseMapper.toResponse(histories));
    }
}