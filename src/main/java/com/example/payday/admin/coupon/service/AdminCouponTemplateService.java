package com.example.payday.admin.coupon.service;

import com.example.payday.admin.coupon.dto.AdminCouponTemplateCreateRequestDto;
import com.example.payday.admin.coupon.dto.AdminCouponTemplateResponseDto;
import com.example.payday.admin.coupon.mapper.AdminCouponTemplateMapper;
import com.example.payday.coupon.domain.CouponTemplate;
import com.example.payday.coupon.repository.CouponTemplateRepository;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class AdminCouponTemplateService {

    private final CouponTemplateRepository repository;
    private final StringRedisTemplate redisTemplate;

    public AdminCouponTemplateService(
            CouponTemplateRepository repository,
            @Qualifier("stringRedisTemplate") StringRedisTemplate redisTemplate
    ) {
        this.repository = repository;
        this.redisTemplate = redisTemplate;
    }

    public Page<AdminCouponTemplateResponseDto> getAllTemplates(Pageable pageable) {
        return repository.findAll(pageable)
                .map(AdminCouponTemplateMapper::toDto);
    }

    @Transactional
    public AdminCouponTemplateResponseDto create(AdminCouponTemplateCreateRequestDto dto) {
        CouponTemplate saved = repository.save(AdminCouponTemplateMapper.toEntity(dto));

        // 선착순 쿠폰일 경우 Redis에 재고 등록
        if (saved.getMaxIssueCount() != null) {
            String stockKey = "coupon:" + saved.getId() + ":stock";
            redisTemplate.opsForValue().set(stockKey, String.valueOf(saved.getMaxIssueCount()));
        }

        return AdminCouponTemplateMapper.toDto(saved);
    }
}
