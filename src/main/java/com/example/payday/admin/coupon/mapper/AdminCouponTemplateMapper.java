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
                .maxIssueCount(dto.getMaxIssueCount())   // ✅ 추가
                .autoIssue(dto.isAutoIssue())           // ✅ 추가
                .issuedCount(0)                          // ✅ 초기값 0으로 설정
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
                .maxIssueCount(template.getMaxIssueCount())  // ✅ 추가
                .issuedCount(template.getIssuedCount())      // ✅ 추가
                .autoIssue(template.isAutoIssue())           // ✅ 추가
                .build();
    }
}