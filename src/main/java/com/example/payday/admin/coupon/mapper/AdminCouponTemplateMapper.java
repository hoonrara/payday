package com.example.payday.admin.coupon.mapper;

import com.example.payday.admin.coupon.dto.AdminCouponTemplateCreateRequestDto;
import com.example.payday.admin.coupon.dto.AdminCouponTemplateResponseDto;
import com.example.payday.coupon.domain.CouponTemplate;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminCouponTemplateMapper {

    public static CouponTemplate toEntity(AdminCouponTemplateCreateRequestDto dto) {
        return CouponTemplate.builder()
                .name(dto.getName())
                .type(dto.getDiscountType())
                .amount(dto.getAmount())
                .maxDiscountAmount(dto.getMaxDiscountAmount())
                .minOrderAmount(dto.getMinOrderAmount())
                .expiredAt(dto.getExpiredAt())
                .maxIssueCount(dto.getMaxIssueCount())
                .autoIssue(dto.isAutoIssue())
                .issuedCount(0) // 최초 생성 시 발급 수는 0
                .build();
    }

    public static AdminCouponTemplateResponseDto toDto(CouponTemplate template) {
        return AdminCouponTemplateResponseDto.builder()
                .id(template.getId())
                .name(template.getName())
                .discountType(template.getType())
                .amount(template.getAmount())
                .maxDiscountAmount(template.getMaxDiscountAmount())
                .minOrderAmount(template.getMinOrderAmount())
                .expiredAt(template.getExpiredAt())
                .maxIssueCount(template.getMaxIssueCount())
                .issuedCount(template.getIssuedCount())
                .autoIssue(template.isAutoIssue())
                .build();
    }
}
