package com.example.payday.coupon.controller;

import com.example.payday.coupon.dto.CouponIssueRequestDto;
import com.example.payday.coupon.dto.CouponIssueResponseDto;
import com.example.payday.coupon.service.CouponIssueService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons/issue")
public class CouponIssueController {

    private final CouponIssueService issueService;

    @PostMapping
    public ResponseEntity<CouponIssueResponseDto> issueCoupon(@RequestBody @Validated CouponIssueRequestDto request) {
        return ResponseEntity.ok(issueService.issueCoupon(request));
    }
}
