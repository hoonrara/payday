package com.example.payday.coupon.repository;

import com.example.payday.coupon.domain.Coupon;
import com.example.payday.coupon.domain.CouponTemplate;
import com.example.payday.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    boolean existsByUserAndTemplate(User user, CouponTemplate template);
    List<Coupon> findByUser(User user);
    Page<Coupon> findByUser(User user, Pageable pageable);



}