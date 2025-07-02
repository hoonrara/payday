package com.example.payday.coupon.service;

import com.example.payday.coupon.domain.Coupon;
import com.example.payday.coupon.domain.CouponTemplate;
import com.example.payday.coupon.dto.CouponIssueRequestDto;
import com.example.payday.coupon.dto.CouponIssueResponseDto;
import com.example.payday.coupon.exception.CouponTemplateNotFoundException;
import com.example.payday.coupon.mapper.CouponMapper;
import com.example.payday.coupon.repository.CouponRepository;
import com.example.payday.coupon.repository.CouponTemplateRepository;
import com.example.payday.user.domain.User;
import com.example.payday.user.exception.UserNotFoundException;
import com.example.payday.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CouponIssueService {

    private final CouponTemplateRepository templateRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;


    public CouponIssueResponseDto issueCoupon(CouponIssueRequestDto request) {
        CouponTemplate template = templateRepository.findById(request.getTemplateId())
                .orElseThrow(CouponTemplateNotFoundException::new); // ✅ 교체

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new); // ✅ 이미 정의돼 있음

        Coupon coupon = CouponMapper.fromTemplate(template, user);
        Coupon saved = couponRepository.save(coupon);

        return CouponIssueResponseDto.builder()
                .couponId(saved.getId())
                .couponName(saved.getName())
                .userId(saved.getUser().getId())
                .build();
    }
}