package com.example.payday.admin.point.controller;

import com.example.payday.admin.point.dto.AdminPointHistorySummaryDto;
import com.example.payday.admin.point.dto.AdminPointMonthlyTotalSummaryDto;
import com.example.payday.admin.point.service.AdminPointService;
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

    @GetMapping("/monthly-summary/total")
    public ResponseEntity<AdminPointMonthlyTotalSummaryDto> getMonthlyTotalSummary() {
        return ResponseEntity.ok(adminPointService.getMonthlyTotalSummary());
    }


}
