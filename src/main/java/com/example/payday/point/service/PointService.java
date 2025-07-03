package com.example.payday.point.service;

import com.example.payday.coupon.domain.Coupon;
import com.example.payday.point.domain.PointHistory;
import com.example.payday.point.dto.PointHistoryResponseDto;
import com.example.payday.point.mapper.PointHistoryMapper;
import com.example.payday.point.repository.PointHistoryRepository;
import com.example.payday.user.domain.User;
import com.example.payday.user.exception.UserNotFoundException;
import com.example.payday.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public List<PointHistoryResponseDto> getHistories(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        List<PointHistory> histories = pointHistoryRepository.findAllByUser(user);

        return histories.stream()
                .map(PointHistoryMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveChargeHistory(User user, int pointToCharge, int paidAmount, String orderId, Coupon coupon) {
        user.addPoint(pointToCharge);
        PointHistory history = PointHistoryMapper.toChargeHistory(user, pointToCharge, paidAmount, orderId, coupon);
        pointHistoryRepository.save(history);
    }

    @Transactional
    public void saveRefundHistory(User user, int amount, String orderId) {
        user.usePoint(amount);
        PointHistory history = PointHistoryMapper.toRefundHistory(user, amount, orderId);
        pointHistoryRepository.save(history);
    }

}
