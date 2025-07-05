package com.example.payday.admin.coupon.mapper;

import com.example.payday.admin.coupon.dto.CouponTemplateCreateRequestDto;
import com.example.payday.admin.coupon.dto.CouponTemplateResponseDto;
import com.example.payday.coupon.domain.CouponTemplate;

public class CouponTemplateMapper {

    public static CouponTemplate toEntity(CouponTemplateCreateRequestDto dto) {
        return CouponTemplate.builder()
                .name(dto.getName())
                .type(dto.getDiscountType())
                .amount(dto.getAmount())
                .maxDiscountAmount(dto.getMaxDiscountAmount())
                .minOrderAmount(dto.getMinOrderAmount())
                .expiredAt(dto.getExpiredAt())
                .build();
    }

    public static CouponTemplateResponseDto toDto(CouponTemplate template) {
        return CouponTemplateResponseDto.builder()
                .id(template.getId())
                .name(template.getName())
                .discountType(template.getType())
                .amount(template.getAmount())
                .maxDiscountAmount(template.getMaxDiscountAmount())
                .minOrderAmount(template.getMinOrderAmount())
                .expiredAt(template.getExpiredAt())
                .build();
    }
}
