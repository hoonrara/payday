package com.example.payday.coupon.service;

import com.example.payday.coupon.domain.Coupon;
import com.example.payday.coupon.domain.CouponTemplate;
import com.example.payday.coupon.dto.CouponIssueRequestDto;
import com.example.payday.coupon.dto.CouponIssueResponseDto;
import com.example.payday.coupon.exception.AlreadyIssuedCouponException;
import com.example.payday.coupon.exception.CouponTemplateNotFoundException;
import com.example.payday.coupon.mapper.CouponMapper;
import com.example.payday.coupon.repository.CouponRepository;
import com.example.payday.coupon.repository.CouponTemplateRepository;
import com.example.payday.user.domain.User;
import com.example.payday.user.exception.UserNotFoundException;
import com.example.payday.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CouponCommandService {

    private final CouponTemplateRepository templateRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    @Transactional
    public CouponIssueResponseDto saveCouponTransactional(CouponIssueRequestDto request) {
        CouponTemplate template = templateRepository.findById(request.getTemplateId())
                .orElseThrow(CouponTemplateNotFoundException::new);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new);

        if (couponRepository.existsByUserAndTemplate(user, template)) {
            throw new AlreadyIssuedCouponException();
        }

        Coupon coupon = CouponMapper.fromTemplate(template, user);
        Coupon saved = couponRepository.save(coupon);

        return CouponMapper.toIssueResponseDto(saved);
    }
}
