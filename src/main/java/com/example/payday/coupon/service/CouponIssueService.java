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

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponIssueService {

    private final CouponTemplateRepository templateRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;

    // 수동 발급
    public CouponIssueResponseDto issueCoupon(CouponIssueRequestDto request) {
        CouponTemplate template = templateRepository.findById(request.getTemplateId())
                .orElseThrow(CouponTemplateNotFoundException::new);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new);

        Coupon coupon = CouponMapper.fromTemplate(template, user);
        Coupon saved = couponRepository.save(coupon);

        return CouponIssueResponseDto.builder()
                .couponId(saved.getId())
                .couponName(saved.getName())
                .userId(saved.getUser().getId())
                .build();
    }

    // ✅ 자동 발급 (조회수 기반, 선착순 포함)
    public void issueAutoCoupons(User user, int viewCount) {
        // issuedCount = 0으로 기준 설정 (이미 발급된 건 아래에서 걸러짐)
        int issuedCount = 0;

        // 필드끼리 비교 대신 파라미터 비교로 변경된 쿼리 메서드 사용
        List<CouponTemplate> templates = templateRepository
                .findByAutoIssueTrueAndViewThresholdLessThanEqualAndMaxIssueCountGreaterThan(viewCount, issuedCount);

        for (CouponTemplate template : templates) {
            boolean alreadyIssued = couponRepository.existsByUserAndTemplate(user, template);
            if (alreadyIssued) continue;

            Coupon coupon = CouponMapper.fromTemplate(template, user);
            couponRepository.save(coupon);

            template.increaseIssuedCount(); // 엔티티에 있어야 하는 메서드
        }
    }
}

