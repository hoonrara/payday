package com.example.payday.coupon.controller;

import com.example.payday.coupon.dto.CouponApplyRequestDto;
import com.example.payday.coupon.dto.CouponResponseDto;
import com.example.payday.coupon.service.CouponService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    /**
     * 쿠폰 미리보기 (얼마 할인되는지 확인)
     */
    @PostMapping("/preview")
    public ResponseEntity<CouponResponseDto> previewCoupon(@RequestBody @Validated CouponApplyRequestDto request) {
        CouponResponseDto response = couponService.previewCoupon(request);
        return ResponseEntity.ok(response);
    }

    /**
     * 유저가 보유한 쿠폰 목록 조회
     */
    @GetMapping("/users/{userId}")
    public ResponseEntity<Page<CouponResponseDto>> getUserCoupons(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        Page<CouponResponseDto> responses = couponService.getCouponsByUser(userId, pageable);
        return ResponseEntity.ok(responses);
    }
}
