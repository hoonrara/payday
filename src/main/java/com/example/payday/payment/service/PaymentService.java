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
     * 포인트 충전 로직 (결제 + 히스토리 저장)
     */
    @Transactional
    public PaymentResultDto chargePoint(PointChargeRequestDto request) {
        PaymentMethod method = PaymentMethod.from(request.getMethod());
        PaymentGateway gateway = gatewayMap.get(method.getBeanName());

        if (gateway == null) throw new UnsupportedPaymentMethodException();

        String orderId = OrderIdGenerator.generate(request.getUserId());
        if (pointHistoryRepository.existsByOrderId(orderId)) {
            throw new DuplicateOrderIdException();
        }

        int pointAmount = request.getPointAmount();
        int discountedAmount = pointAmount;
        Coupon appliedCoupon = null;

        // 쿠폰 적용 시 할인 반영
        if (request.getCouponId() != null) {
            discountedAmount = couponService.applyDiscountForPayment(request.getCouponId(), pointAmount);
            appliedCoupon = couponService.getCouponById(request.getCouponId());
        }

        // 전역 할인 정책 적용 (예: 프로필 기반 이벤트 할인)
        int finalPaidAmount = discountPolicy.calculateDiscount(discountedAmount);

        // 결제 게이트웨이 실행
        PaymentResultDto result = gateway.pay(
                PaymentMapper.toPaymentRequest(request, finalPaidAmount, orderId)
        );

        if (result.getStatus() != PaymentStatus.DONE) {
            throw new InvalidPaymentException();
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new);

        // 포인트 충전 내역 저장
        pointService.saveChargeHistory(user, pointAmount, finalPaidAmount, orderId, appliedCoupon);

        return result;
    }

    /**
     * 포인트 환불 요청 처리
     */
    @Transactional
    public RefundResultDto refundPoint(RefundRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new);

        String originalOrderId = request.getOrderId();
        String refundOrderId = originalOrderId + "-REFUND";

        // 환불 히스토리 저장 및 포인트 회수
        return pointService.saveRefundHistory(originalOrderId, refundOrderId, user);
    }
}
