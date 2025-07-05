package com.example.payday.admin.coupon.service;

import com.example.payday.admin.coupon.dto.CouponTemplateCreateRequestDto;
import com.example.payday.admin.coupon.dto.CouponTemplateResponseDto;
import com.example.payday.admin.coupon.mapper.CouponTemplateMapper;
import com.example.payday.coupon.repository.CouponTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponTemplateService {

    private final CouponTemplateRepository repository;

    public Page<CouponTemplateResponseDto> getAllTemplates(Pageable pageable) {
        return repository.findAll(pageable)
                .map(CouponTemplateMapper::toDto);
    }

    public CouponTemplateResponseDto create(CouponTemplateCreateRequestDto dto) {
        var saved = repository.save(CouponTemplateMapper.toEntity(dto));
        return CouponTemplateMapper.toDto(saved);
    }}