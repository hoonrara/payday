package com.example.payday.admin.coupon.controller;

import com.example.payday.admin.coupon.dto.CouponTemplateCreateRequestDto;
import com.example.payday.admin.coupon.dto.CouponTemplateResponseDto;
import com.example.payday.admin.coupon.service.CouponTemplateService;
import com.example.payday.global.dto.PagedResponse;
import com.example.payday.global.mapper.PagedResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/coupon-templates")
@RequiredArgsConstructor
public class CouponTemplateController {

    private final CouponTemplateService service;

    @PostMapping
    public ResponseEntity<CouponTemplateResponseDto> create(@RequestBody CouponTemplateCreateRequestDto dto) {
        CouponTemplateResponseDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @GetMapping
    public ResponseEntity<PagedResponse<CouponTemplateResponseDto>> getAll(Pageable pageable) {
        Page<CouponTemplateResponseDto> page = service.getAllTemplates(pageable);
        return ResponseEntity.ok(PagedResponseMapper.toResponse(page));
    }
}