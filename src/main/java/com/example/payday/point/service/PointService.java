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

    /**
     * 사용자의 포인트 히스토리 목록 조회
     */
    @Transactional(readOnly = true)
    public Page<PointHistoryResponseDto> getPointHistoriesByUser(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Page<PointHistory> histories = pointHistoryRepository.findAllByUser(user, pageable);
        return histories.map(PointHistoryMapper::toDto);
    }

    /**
     * 포인트 충전 기록 저장 (포인트 적립 + 이력 저장)
     */
    @Transactional
    public void saveChargeHistory(User user, int pointToCharge, int paidAmount, String orderId, Coupon coupon) {
        user.addPoint(pointToCharge);

        PointHistory history = PointHistoryMapper.toChargeHistory(
                user, pointToCharge, paidAmount, orderId, coupon
        );

        pointHistoryRepository.save(history);
    }

    /**
     * 포인트 환불 기록 저장 (포인트 차감 + 환불 이력)
     */
    @Transactional
    public RefundResultDto saveRefundHistory(String originalOrderId, String refundOrderId, User user) {
        PointHistory chargeHistory = pointHistoryRepository
                .findByOrderIdAndType(originalOrderId, PointHistoryType.CHARGE)
                .orElseThrow(PointHistoryNotFoundException::new);

        if (pointHistoryRepository.existsByOrderId(refundOrderId)) {
            throw new DuplicateOrderIdException();
        }

        int refundAmount = chargeHistory.getPointAmount();

        user.usePoint(refundAmount); // 포인트 회수

        PointHistory refundHistory = PointHistoryMapper.toRefundHistory(user, refundAmount, refundOrderId);

        try {
            pointHistoryRepository.save(refundHistory);
        } catch (DataIntegrityViolationException e) {
            // 멀티쓰레드 환경에서 중복 insert 방지
            throw new DuplicateOrderIdException();
        }

        return PointHistoryMapper.toRefundResultDto(refundHistory);
    }
}
