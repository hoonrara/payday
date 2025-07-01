package com.example.payday.point.service;

import com.example.payday.payment.discount.DiscountPolicy;
import com.example.payday.payment.exception.InvalidPaymentException;
import com.example.payday.point.domain.PointHistory;
import com.example.payday.point.domain.type.PointHistoryType;
import com.example.payday.point.dto.PointChargeRequestDto;
import com.example.payday.point.repository.PointHistoryRepository;
import com.example.payday.user.domain.User;
import com.example.payday.user.repository.UserRepository;
import com.example.payday.user.exception.UserNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@RequiredArgsConstructor
public class PointService {

    private final PointHistoryRepository pointHistoryRepository;
    private final UserRepository userRepository;
    private final DiscountPolicy discountPolicy;

    @Transactional
    public void chargePoint(PointChargeRequestDto request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(UserNotFoundException::new);

        int discountedAmount = discountPolicy.calculateDiscount(request.getAmount());


        user.addPoint(request.getAmount());

        PointHistory history = PointHistory.builder()
                .user(user)
                .amount(discountedAmount)
                .type(PointHistoryType.CHARGE)
                .currentPoint(user.getPoint())
                .build();

        pointHistoryRepository.save(history);
    }

    @Transactional
    public void usePoint(Long userId, int amount) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        if (user.getPoint() < amount) {
            throw new InvalidPaymentException("포인트가 부족합니다.");
        }

        user.usePoint(amount); // 포인트 차감

        PointHistory history = PointHistory.builder()
                .user(user)
                .amount(amount)
                .type(PointHistoryType.USE) // 충전이 아닌 사용
                .build();

        pointHistoryRepository.save(history);
    }
}

