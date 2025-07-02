package com.example.payday.coupon.controller;

import com.example.payday.coupon.dto.CouponApplyRequestDto;
import com.example.payday.coupon.dto.CouponResponseDto;
import com.example.payday.coupon.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/coupons")
public class CouponPreviewController {

    private final CouponService couponService;

    /**
     * 쿠폰 미리보기 (적용 가능한지, 얼마 할인되는지 확인)
     */
    @PostMapping("/preview")
    public ResponseEntity<CouponResponseDto> previewCoupon(@RequestBody CouponApplyRequestDto request) {
        CouponResponseDto result = couponService.previewCoupon(request);
        return ResponseEntity.ok(result);
    }
}