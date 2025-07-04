package com.example.payday.admin.point;

import com.example.payday.point.dto.PointHistoryResponseDto;
import com.example.payday.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api/admin/users/{userId}/points")
public class AdminPointController {

    private final PointService pointService; // ✅ 이거만 있으면 됨

    @GetMapping("/histories")
    public ResponseEntity<List<PointHistoryResponseDto>> getHistories(@PathVariable Long userId) {
        return ResponseEntity.ok(pointService.getHistories(userId));
    }
}
