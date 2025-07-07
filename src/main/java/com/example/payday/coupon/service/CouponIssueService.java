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
    private final CouponCommandService couponCommandService;

    private final RedisTemplate<String, String> redisTemplate;

    public CouponIssueService(
            CouponTemplateRepository templateRepository,
            CouponRepository couponRepository,
            CouponCommandService couponCommandService,
            @Qualifier("luaRedisTemplate") RedisTemplate<String, String> redisTemplate // ✅ 이거!
    ) {
        this.templateRepository = templateRepository;
        this.couponRepository = couponRepository;
        this.couponCommandService = couponCommandService;
        this.redisTemplate = redisTemplate;
    }



    private static final String COUPON_STOCK_LUA_SCRIPT =
            "local stock = redis.call('get', KEYS[1])\n" +
                    "if (not stock) then return -1 end\n" +
                    "if (tonumber(stock) <= 0) then return 0 end\n" +
                    "redis.call('decr', KEYS[1])\n" +
                    "return 1";

    public CouponIssueResponseDto issueCoupon(CouponIssueRequestDto request) {
        String stockKey = "coupon:" + request.getTemplateId() + ":stock";

        Long result = redisTemplate.execute(getLuaScript(), Collections.singletonList(stockKey));
        if (result == null || result == -1L) {
            throw new IllegalStateException("Redis에 재고 키가 없습니다.");
        } else if (result <= 0L) {
            throw new CouponOutOfStockException();
        }

        try {
            return couponCommandService.saveCouponTransactional(request);
        } catch (AlreadyIssuedCouponException e) {
            rollbackRedisStock(stockKey);
            throw e;
        } catch (UserNotFoundException | CouponTemplateNotFoundException e) {
            rollbackRedisStock(stockKey);
            throw e;
        } catch (Exception e) {
            rollbackRedisStock(stockKey);
            throw e;
        }
    }

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


    public void issueAutoCoupons(User user, int viewCount) {
        int issuedCount = 0;
        List<CouponTemplate> templates = templateRepository
                .findByAutoIssueTrueAndViewThresholdLessThanEqualAndMaxIssueCountGreaterThan(viewCount, issuedCount);

        for (CouponTemplate template : templates) {
            if (couponRepository.existsByUserAndTemplate(user, template)) continue;

            Coupon coupon = CouponMapper.fromTemplate(template, user);
            couponRepository.save(coupon);

            template.increaseIssuedCount();
            templateRepository.save(template);
        }
    }
}
