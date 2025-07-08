package com.example.payday.coupon.service;

import com.example.payday.coupon.domain.Coupon;
import com.example.payday.coupon.domain.CouponTemplate;
import com.example.payday.coupon.dto.CouponIssueRequestDto;
import com.example.payday.coupon.dto.CouponIssueResponseDto;
import com.example.payday.coupon.exception.AlreadyIssuedCouponException;
import com.example.payday.coupon.exception.CouponOutOfStockException;
import com.example.payday.coupon.exception.CouponTemplateNotFoundException;
import com.example.payday.coupon.mapper.CouponMapper;
import com.example.payday.coupon.repository.CouponRepository;
import com.example.payday.coupon.repository.CouponTemplateRepository;
import com.example.payday.user.domain.User;
import com.example.payday.user.exception.UserNotFoundException;
import com.example.payday.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.script.DefaultRedisScript;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

@Service
@Slf4j
public class CouponIssueService {

    private final CouponTemplateRepository templateRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final RedisTemplate<String, String> redisTemplate;

    public CouponIssueService(
            CouponTemplateRepository templateRepository,
            CouponRepository couponRepository,
            UserRepository userRepository,
            @Qualifier("luaRedisTemplate") RedisTemplate<String, String> redisTemplate
    ) {
        this.templateRepository = templateRepository;
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
        this.redisTemplate = redisTemplate;
    }

    private static final String COUPON_STOCK_LUA_SCRIPT =
            "local stock = redis.call('get', KEYS[1])\n" +
                    "if (not stock) then return -1 end\n" +
                    "if (tonumber(stock) <= 0) then return 0 end\n" +
                    "redis.call('decr', KEYS[1])\n" +
                    "return 1";

    /**
     * 선착순 쿠폰 발급 - Redis Lua 스크립트로 재고 관리
     */
    public CouponIssueResponseDto issueCoupon(CouponIssueRequestDto request) {
        String stockKey = "coupon:" + request.getTemplateId() + ":stock";

        Long result = redisTemplate.execute(getLuaScript(), Collections.singletonList(stockKey));
        if (result == null || result == -1L) {
            throw new IllegalStateException("Redis에 재고 키가 없습니다.");
        } else if (result <= 0L) {
            throw new CouponOutOfStockException();
        }

        try {
            return saveCoupon(request); // Redis 성공 후 실제 DB 저장
        } catch (AlreadyIssuedCouponException |
                 UserNotFoundException |
                 CouponTemplateNotFoundException e) {
            rollbackRedisStock(stockKey);
            throw e;
        } catch (Exception e) {
            rollbackRedisStock(stockKey);
            throw new RuntimeException("쿠폰 발급 중 알 수 없는 오류", e);
        }
    }

    @Transactional
    public CouponIssueResponseDto saveCoupon(CouponIssueRequestDto request) {
        CouponTemplate template = templateRepository.findById(request.getTemplateId())
                .orElseThrow(CouponTemplateNotFoundException::new);

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new);

        if (couponRepository.existsByUserAndTemplate(user, template)) {
            throw new AlreadyIssuedCouponException();
        }

        Coupon coupon = CouponMapper.fromTemplate(template, user);
        Coupon saved = couponRepository.save(coupon);

        return CouponMapper.toIssueResponseDto(saved);
    }

    // Redis 재고 복구
    private void rollbackRedisStock(String stockKey) {
        redisTemplate.opsForValue().increment(stockKey);
        log.warn("💥 Redis 재고 복구 수행됨! key={}", stockKey);
    }

    private DefaultRedisScript<Long> getLuaScript() {
        DefaultRedisScript<Long> script = new DefaultRedisScript<>();
        script.setScriptText(COUPON_STOCK_LUA_SCRIPT);
        script.setResultType(Long.class);
        return script;
    }

    /**
     * 자동 발급 쿠폰 처리 (조회수 기반, 선착순 체크 포함)
     */
    @Transactional
    public void issueAutoCoupons(User user, int viewCount) {
        int issuedCount = 0;
        List<CouponTemplate> templates = templateRepository
                .findByAutoIssueTrueAndViewThresholdLessThanEqualAndMaxIssueCountGreaterThan(viewCount, issuedCount);

        for (CouponTemplate template : templates) {
            if (couponRepository.existsByUserAndTemplate(user, template)) continue;

            Coupon coupon = CouponMapper.fromTemplate(template, user);
            couponRepository.save(coupon);

            template.increaseIssuedCount();// DB에서 issuedCount 증가
            templateRepository.save(template);
        }
    }
}
