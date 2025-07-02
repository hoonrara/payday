package com.example.payday.coupon.controller;

import com.example.payday.coupon.dto.CouponCreateRequestDto;
import com.example.payday.coupon.service.CouponCommandService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/coupons")
@RequiredArgsConstructor
public class CouponController {

    private final CouponCommandService couponCommandService;

    @PostMapping
    public ResponseEntity<Void> createCoupon(@RequestBody CouponCreateRequestDto request) {
        couponCommandService.createCoupon(request);
        return ResponseEntity.ok().build();
    }
}