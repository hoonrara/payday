package com.example.payday.point.service;

import com.example.payday.coupon.domain.Coupon;
import com.example.payday.point.domain.PointHistory;
import com.example.payday.point.domain.type.PointHistoryType;
import com.example.payday.point.dto.PointHistoryResponseDto;
import com.example.payday.point.dto.RefundResultDto;
import com.example.payday.point.exception.DuplicateOrderIdException;
import com.example.payday.point.exception.PointHistoryNotFoundException;
import com.example.payday.point.mapper.PointHistoryMapper;
import com.example.payday.point.repository.PointHistoryRepository;
import com.example.payday.user.domain.User;
import com.example.payday.user.exception.UserNotFoundException;
import com.example.payday.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PointService {

    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    @Transactional(readOnly = true)
    public Page<PointHistoryResponseDto> getPointHistoriesByUser(Long userId, Pageable pageable) {
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
    public RefundResultDto saveRefundHistory(String originalOrderId, String refundOrderId, User user) {
        PointHistory chargeHistory = pointHistoryRepository
                .findByOrderIdAndType(originalOrderId, PointHistoryType.CHARGE)
                .orElseThrow(PointHistoryNotFoundException::new);

        if (pointHistoryRepository.existsByOrderId(refundOrderId)) {
            throw new DuplicateOrderIdException();
        }

        int refundAmount = chargeHistory.getPointAmount();
        user.usePoint(refundAmount);

        PointHistory refundHistory = PointHistoryMapper.toRefundHistory(user, refundAmount, refundOrderId);
        pointHistoryRepository.save(refundHistory);

        try {
            pointHistoryRepository.save(refundHistory);
        } catch (DataIntegrityViolationException e) {
            throw new DuplicateOrderIdException(); // 동시에 요청이 온 경우 중복 insert 방지
        }

        return PointHistoryMapper.toRefundResultDto(refundHistory);
    }
}