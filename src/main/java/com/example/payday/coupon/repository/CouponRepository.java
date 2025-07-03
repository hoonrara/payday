package com.example.payday.coupon.repository;

import com.example.payday.coupon.domain.Coupon;
import com.example.payday.coupon.domain.CouponTemplate;
import com.example.payday.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    boolean existsByUserAndTemplate(User user, CouponTemplate template);

}