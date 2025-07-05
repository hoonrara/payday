package com.example.payday.admin.point.controller;

import com.example.payday.admin.point.dto.AdminPointHistoryDto;
import com.example.payday.admin.point.dto.AdminPointHistorySummaryDto;
import com.example.payday.admin.point.dto.AdminPointMonthlyTotalSummaryDto;
import com.example.payday.admin.point.service.AdminPointService;
import com.example.payday.global.dto.PagedResponse;
import com.example.payday.global.mapper.PagedResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/points")
@RequiredArgsConstructor
public class AdminPointController {

    private final AdminPointService adminPointService;

    // ✅ 특정 유저 포인트 요약 조회
    @GetMapping("/{userId}")
    public ResponseEntity<AdminPointHistorySummaryDto> getUserPointHistory(@PathVariable Long userId, Pageable pageable) {
        return ResponseEntity.ok(adminPointService.getAdminHistorySummary(userId, pageable));
    }

    // ✅ 전체 포인트 내역 조회
    @GetMapping("/histories")
    public ResponseEntity<PagedResponse<AdminPointHistoryDto>> getAllPointHistories(Pageable pageable) {
        return ResponseEntity.ok(PagedResponseMapper.toResponse(adminPointService.getAllHistories(pageable)));
    }

    @GetMapping("/monthly-summary/total")
    public ResponseEntity<AdminPointMonthlyTotalSummaryDto> getMonthlyTotalSummary() {
        return ResponseEntity.ok(adminPointService.getMonthlyTotalSummary());
    }


}
