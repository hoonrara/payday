package com.example.payday.point.controller;

import com.example.payday.point.dto.PointHistoryResponseDto;
import com.example.payday.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @GetMapping("/{userId}/histories")
    public List<PointHistoryResponseDto> getHistories(@PathVariable Long userId) {
        return pointService.getHistories(userId);
    }

}