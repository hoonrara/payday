package com.example.payday.payment.service;

import com.example.payday.coupon.domain.Coupon;
import com.example.payday.coupon.service.CouponService;
import com.example.payday.global.util.OrderIdGenerator;
import com.example.payday.payment.discount.DiscountPolicy;
import com.example.payday.payment.dto.PaymentResultDto;
import com.example.payday.payment.exception.InvalidPaymentException;
import com.example.payday.payment.exception.UnsupportedPaymentMethodException;
import com.example.payday.payment.gateway.PaymentGateway;
import com.example.payday.payment.mapper.PaymentMapper;
import com.example.payday.payment.type.PaymentMethod;
import com.example.payday.payment.type.PaymentStatus;
import com.example.payday.point.domain.PointHistory;
import com.example.payday.point.domain.type.PointHistoryType;
import com.example.payday.point.dto.PointChargeRequestDto;
import com.example.payday.point.dto.RefundRequestDto;
import com.example.payday.point.dto.RefundResultDto;
import com.example.payday.point.exception.DuplicateOrderIdException;
import com.example.payday.point.exception.InsufficientPointException;
import com.example.payday.point.exception.PointHistoryNotFoundException;
import com.example.payday.point.repository.PointHistoryRepository;
import com.example.payday.point.service.PointService;
import com.example.payday.user.domain.User;
import com.example.payday.user.exception.UserNotFoundException;
import com.example.payday.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

@RequiredArgsConstructor
@Service
public class PaymentService {

    private final Map<String, PaymentGateway> gatewayMap;
    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final CouponService couponService;
    private final DiscountPolicy discountPolicy;
    private final PointService pointService;

    /**
     * 실제 결제 → 포인트 충전
     */
    @Transactional
    public PaymentResultDto chargePoint(PointChargeRequestDto request) {
        // 1. 결제 수단 처리
        PaymentMethod method = PaymentMethod.from(request.getMethod());
        PaymentGateway gateway = gatewayMap.get(method.getBeanName());
        if (gateway == null) {
            throw new UnsupportedPaymentMethodException();
        }

        // 2. 주문 ID 생성 및 중복 확인
        String orderId = OrderIdGenerator.generate(request.getUserId());
        if (pointHistoryRepository.existsByOrderId(orderId)) {
            throw new DuplicateOrderIdException();
        }

        // 3. 충전 포인트 계산
        int pointAmount = request.getPointAmount();
        int discountedAmount = pointAmount;
        Coupon appliedCoupon = null;

        // 4. 쿠폰 적용
        if (request.getCouponId() != null) {
            discountedAmount = couponService.applyDiscountForPayment(request.getCouponId(), pointAmount);
            appliedCoupon = couponService.getCouponById(request.getCouponId());
        }

        // 5. 전역 할인 정책 적용
        int finalPaidAmount = discountPolicy.calculateDiscount(discountedAmount);

        // 6. 결제 수행
        PaymentResultDto result = gateway.pay(
                PaymentMapper.toPaymentRequest(request, finalPaidAmount, orderId)
        );

        if (result.getStatus() != PaymentStatus.DONE) {
            throw new InvalidPaymentException();
        }

        // 7. 유저 포인트 적립
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new);

        pointService.saveChargeHistory(user, pointAmount, finalPaidAmount, orderId, appliedCoupon);

        // 8. 결제 결과 반환
        return result;
    }

    /**
     * 결제 기반 환불 처리
     */
    @Transactional
    public RefundResultDto refundPoint(RefundRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new);

        String originalOrderId = request.getOrderId();
        String refundOrderId = originalOrderId + "-REFUND";

        if (pointHistoryRepository.existsByOrderId(refundOrderId)) {
            throw new DuplicateOrderIdException();
        }

        PointHistory chargeHistory = pointHistoryRepository
                .findByOrderIdAndType(originalOrderId, PointHistoryType.CHARGE)
                .orElseThrow(PointHistoryNotFoundException::new);

        int refundAmount = chargeHistory.getPointAmount();

        if (user.getPoint() < refundAmount) {
            throw new InsufficientPointException();
        }

        return pointService.saveRefundHistory(user, refundAmount, refundOrderId);
    }
}
