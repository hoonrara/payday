package com.example.payday.point.controller;

import com.example.payday.global.dto.PagedResponse;
import com.example.payday.global.mapper.PagedResponseMapper;
import com.example.payday.point.dto.PointHistoryResponseDto;
import com.example.payday.point.service.PointService;
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
public class PointController {

    private final PointService pointService;

    @GetMapping("/histories")
    public ResponseEntity<PagedResponse<PointHistoryResponseDto>> getHistories(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        Page<PointHistoryResponseDto> histories = pointService.getPointHistoriesByUser(userId, pageable);
        return ResponseEntity.ok(PagedResponseMapper.toResponse(histories));
    }
}