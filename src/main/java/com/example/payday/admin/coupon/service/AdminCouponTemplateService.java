package com.example.payday.admin.coupon.service;

import com.example.payday.admin.coupon.dto.AdminCouponTemplateCreateRequestDto;
import com.example.payday.admin.coupon.dto.AdminCouponTemplateResponseDto;
import com.example.payday.admin.coupon.mapper.AdminCouponTemplateMapper;
import com.example.payday.coupon.repository.CouponTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminCouponTemplateService {

    private final CouponTemplateRepository repository;

    public Page<AdminCouponTemplateResponseDto> getAllTemplates(Pageable pageable) {
        return repository.findAll(pageable)
                .map(AdminCouponTemplateMapper::toDto);
    }

    public AdminCouponTemplateResponseDto create(AdminCouponTemplateCreateRequestDto dto) {
        var saved = repository.save(AdminCouponTemplateMapper.toEntity(dto));
        return AdminCouponTemplateMapper.toDto(saved);
    }}