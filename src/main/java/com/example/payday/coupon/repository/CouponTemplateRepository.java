package com.example.payday.coupon.repository;

import com.example.payday.coupon.domain.CouponTemplate;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponTemplateRepository extends JpaRepository<CouponTemplate, Long> {
}
