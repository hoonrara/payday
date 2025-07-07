package com.example.payday.coupon.controller;

import com.example.payday.coupon.dto.CouponIssueRequestDto;
import com.example.payday.coupon.dto.CouponIssueResponseDto;
import com.example.payday.coupon.service.CouponIssueService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "A03. [USER] 쿠폰 발급 API", description = "쿠폰 직접 발급")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons/issue")
public class CouponIssueController {

    private final CouponIssueService issueService;

    @Operation(summary = "쿠폰 발급", description = "사용자에게 쿠폰을 직접 발급합니다.")
    @ApiResponse(responseCode = "200", description = "정상 응답")
    @PostMapping
    public ResponseEntity<CouponIssueResponseDto> issueCoupon(@RequestBody @Validated CouponIssueRequestDto request) {
        return ResponseEntity.ok(issueService.issueCoupon(request));
    }
}