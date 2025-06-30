package com.example.payday.point.controller;

import com.example.payday.point.dto.PointChargeRequestDto;
import com.example.payday.point.service.PointService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/points")
@RequiredArgsConstructor
public class PointController {

    private final PointService pointService;

    @PostMapping("/charge")
    public ResponseEntity<Void> chargePoint(@RequestBody PointChargeRequestDto request) {
        pointService.chargePoint(request);
        return ResponseEntity.ok().build();
    }
}