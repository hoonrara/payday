package com.example.payday.coupon.controller;

import com.example.payday.coupon.dto.CouponApplyRequestDto;
import com.example.payday.coupon.dto.CouponResponseDto;
import com.example.payday.coupon.service.CouponService;
import com.example.payday.global.dto.PagedResponse;
import com.example.payday.global.mapper.PagedResponseMapper;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "A04. [USER] 쿠폰 API", description = "쿠폰 미리보기 및 사용자 보유 쿠폰 조회")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    @Operation(summary = "쿠폰 미리보기", description = "결제 금액과 쿠폰 정보를 기반으로 실제 할인 금액을 미리 계산합니다.")
    @ApiResponse(responseCode = "200", description = "정상 응답")
    @PostMapping("/preview")
    public ResponseEntity<CouponResponseDto> previewCoupon(@RequestBody @Validated CouponApplyRequestDto request) {
        return ResponseEntity.ok(couponService.previewCoupon(request));
    }

    @Operation(
            summary = "사용자 쿠폰 목록 조회",
            description = "사용자가 보유한 쿠폰 목록을 페이지네이션으로 조회합니다."
    )
    @ApiResponse(responseCode = "200", description = "정상 응답")
    @GetMapping("/users/{userId}")
    public ResponseEntity<PagedResponse<CouponResponseDto>> getUserCoupons(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        return ResponseEntity.ok(PagedResponseMapper.toResponse(couponService.getCouponsByUser(userId, pageable)));
    }
}