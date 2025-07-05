package com.example.payday.point.service;

import com.example.payday.coupon.domain.Coupon;
import com.example.payday.point.domain.PointHistory;
import com.example.payday.point.dto.PointHistoryResponseDto;
import com.example.payday.point.dto.RefundResultDto;
import com.example.payday.point.mapper.PointHistoryMapper;
import com.example.payday.point.repository.PointHistoryRepository;
import com.example.payday.user.domain.User;
import com.example.payday.user.exception.UserNotFoundException;
import com.example.payday.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public Page<PointHistoryResponseDto> getHistories(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Page<PointHistory> histories = pointHistoryRepository.findAllByUser(user, pageable);

        return histories.map(PointHistoryMapper::toDto);
    }

    @Transactional
    public void saveChargeHistory(User user, int pointToCharge, int paidAmount, String orderId, Coupon coupon) {
        user.addPoint(pointToCharge);
        PointHistory history = PointHistoryMapper.toChargeHistory(user, pointToCharge, paidAmount, orderId, coupon);
        pointHistoryRepository.save(history);
    }

    @Transactional
    public RefundResultDto saveRefundHistory(User user, int amount, String orderId) {
        user.usePoint(amount);
        PointHistory history = PointHistoryMapper.toRefundHistory(user, amount, orderId);
        pointHistoryRepository.save(history);

        return RefundResultDto.builder()
                .paymentKey(orderId) // 실 연동 시 paymentKey 사용 가능
                .cancelAmount(amount)
                .cancelReason("사용자 요청")  // 하드코딩 가능, 필요 시 파라미터로 뺄 수도 있음
                .canceledAt(history.getCreatedAt())
                .status("CANCELED")
                .build();
    }

}
