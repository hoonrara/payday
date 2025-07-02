package com.example.payday.payment.service;

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

    private final Map<String, PaymentGateway> gatewayMap; // 💡 핵심 포인트
    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;
    private final PaymentMapper paymentMapper;

    @Transactional
    public void chargePointWithPayment(PointChargeRequestDto request) {
        PaymentMethod method = PaymentMethod.from(request.getMethod());
        PaymentGateway gateway = gatewayMap.get(method.name().toLowerCase()); // "TOSS" → "toss"

        if (gateway == null) {
            throw new UnsupportedPaymentMethodException();
        }

        PaymentResultDto result = gateway.pay(paymentMapper.toPaymentRequest(request));

        if (result.getStatus() != PaymentStatus.DONE) {
            throw new InvalidPaymentException();
        }

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new);

        user.addPoint(result.getAmount());

        PointHistory history = PointHistoryMapper.toChargeHistory(user, result.getAmount());


        pointHistoryRepository.save(history);
    }
}