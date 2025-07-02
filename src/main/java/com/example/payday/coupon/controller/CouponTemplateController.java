package com.example.payday.coupon.controller;

import com.example.payday.coupon.dto.CouponTemplateCreateRequestDto;
import com.example.payday.coupon.dto.CouponTemplateResponseDto;
import com.example.payday.coupon.service.CouponTemplateService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupon-templates")
@RequiredArgsConstructor
public class CouponTemplateController {

    private final CouponTemplateService service;

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CouponTemplateCreateRequestDto dto) {
        service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping
    public ResponseEntity<List<CouponTemplateResponseDto>> getAll() {
        return ResponseEntity.ok(service.getAllTemplates());
    }
}