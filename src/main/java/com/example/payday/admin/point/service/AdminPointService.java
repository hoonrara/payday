package com.example.payday.admin.point.service;

import com.example.payday.admin.point.dto.AdminPointHistoryDto;
import com.example.payday.admin.point.dto.AdminPointHistorySummaryDto;
import com.example.payday.admin.point.dto.AdminPointMonthlyTotalSummaryDto;
import com.example.payday.admin.point.mapper.AdminPointMapper;
import com.example.payday.point.domain.PointHistory;
import com.example.payday.point.domain.type.PointHistoryType;
import com.example.payday.point.repository.PointHistoryRepository;
import com.example.payday.user.domain.User;
import com.example.payday.user.exception.UserNotFoundException;
import com.example.payday.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminPointService {

    private final UserRepository userRepository;
    private final PointHistoryRepository pointHistoryRepository;

    public AdminPointHistorySummaryDto getAdminHistorySummary(Long userId, Pageable pageable) {
        User user = userRepository.findById(userId)
                .orElseThrow(UserNotFoundException::new);

        Page<PointHistory> historyPage = pointHistoryRepository.findAllByUser(user, pageable);

        return AdminPointMapper.toSummaryDto(user, historyPage);
    }
    public Page<AdminPointHistoryDto> getAllHistories(Pageable pageable) {
        Page<PointHistory> histories = pointHistoryRepository.findAllBy(pageable);
        return histories.map(AdminPointMapper::toDto);
    }

    public AdminPointMonthlyTotalSummaryDto getMonthlyTotalSummary() {
        LocalDateTime startOfMonth = LocalDateTime.now()
                .withDayOfMonth(1)
                .withHour(0)
                .withMinute(0)
                .withSecond(0)
                .withNano(0);

        List<PointHistory> allHistories = pointHistoryRepository.findAllByCreatedAtAfter(startOfMonth);

        int totalCharge = allHistories.stream()
                .filter(h -> h.getType() == PointHistoryType.CHARGE)
                .mapToInt(PointHistory::getPointAmount)
                .sum();

        int totalRefund = allHistories.stream()
                .filter(h -> h.getType() == PointHistoryType.REFUND)
                .mapToInt(PointHistory::getPointAmount)
                .sum();

        return AdminPointMapper.toTotalSummaryDto(totalCharge, totalRefund);
    }

}
