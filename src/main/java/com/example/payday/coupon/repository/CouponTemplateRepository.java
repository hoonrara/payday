package com.example.payday.coupon.repository;

import com.example.payday.coupon.domain.CouponTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponTemplateRepository extends JpaRepository<CouponTemplate, Long> {
    List<CouponTemplate> findByAutoIssueTrueAndViewThresholdLessThanEqualAndMaxIssueCountGreaterThan(
            int viewCount,
            int issuedCount
    );

}
