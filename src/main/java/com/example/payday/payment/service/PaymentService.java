package com.example.payday.payment.service;

import com.example.payday.coupon.domain.Coupon;
import com.example.payday.coupon.service.CouponService;
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
import com.example.payday.point.exception.InsufficientPointException;
import com.example.payday.point.exception.PointHistoryNotFoundException;
import com.example.payday.point.mapper.PointHistoryMapper;
import com.example.payday.point.repository.PointHistoryRepository;
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
    private final PaymentMapper paymentMapper;
    private final CouponService couponService;

    /**
     * 실제 결제 → 포인트 충전
     */
    @Transactional
    public void chargePoint(PointChargeRequestDto request) {
        PaymentMethod method = PaymentMethod.from(request.getMethod());
        PaymentGateway gateway = gatewayMap.get(method.name().toLowerCase());

        if (gateway == null) {
            throw new UnsupportedPaymentMethodException();
        }

        int finalAmount = request.getAmount();
        Coupon appliedCoupon = null;

        // 쿠폰 적용
        if (request.getCouponId() != null) {
            finalAmount = couponService.applyDiscountForPayment(request.getCouponId(), finalAmount);
            appliedCoupon = couponService.getCouponById(request.getCouponId()); // Coupon 객체 조회
        }

        // 결제 요청
        PaymentResultDto result = gateway.pay(paymentMapper.toPaymentRequest(request, finalAmount));
        if (result.getStatus() != PaymentStatus.DONE) {
            throw new InvalidPaymentException();
        }

        // 포인트 적립
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new);

        user.addPoint(result.getAmount());

        PointHistory history = PointHistoryMapper.toChargeHistory(
                user,
                result.getAmount(),
                result.getOrderId(),
                appliedCoupon // 연관된 쿠폰 넣기
        );
        pointHistoryRepository.save(history);
    }

    /**
     * 결제 기반 환불 처리
     */
    @Transactional
    public void refundPoint(RefundRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new);

        String orderId = request.getOrderId();

        PointHistory chargeHistory = pointHistoryRepository
                .findByOrderIdAndType(orderId, PointHistoryType.CHARGE)
                .orElseThrow(PointHistoryNotFoundException::new);

        int refundAmount = chargeHistory.getAmount();

        if (user.getPoint() < refundAmount) {
            throw new InsufficientPointException();
        }

        user.usePoint(refundAmount);

        PointHistory refundHistory = PointHistoryMapper.toRefundHistory(user, refundAmount, orderId);
        pointHistoryRepository.save(refundHistory);
    }
}
