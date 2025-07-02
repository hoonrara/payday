package com.example.payday.coupon.service;

import com.example.payday.coupon.dto.CouponTemplateCreateRequestDto;
import com.example.payday.coupon.dto.CouponTemplateResponseDto;
import com.example.payday.coupon.mapper.CouponTemplateMapper;
import com.example.payday.coupon.repository.CouponTemplateRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CouponTemplateService {

    private final CouponTemplateRepository repository;

    public List<CouponTemplateResponseDto> getAllTemplates() {
        return repository.findAll()
                .stream()
                .map(CouponTemplateMapper::toDto)
                .toList();
    }

    public void create(CouponTemplateCreateRequestDto dto) {
        repository.save(CouponTemplateMapper.toEntity(dto));
    }
}