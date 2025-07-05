package com.example.payday.admin.point.mapper;

import com.example.payday.admin.point.dto.AdminPointHistoryDto;
import com.example.payday.admin.point.dto.AdminPointHistorySummaryDto;
import com.example.payday.admin.point.dto.AdminPointMonthlyTotalSummaryDto;
import com.example.payday.point.domain.PointHistory;
import com.example.payday.point.domain.type.PointHistoryType;
import com.example.payday.point.dto.PointHistoryResponseDto;
import com.example.payday.point.mapper.PointHistoryMapper;
import com.example.payday.user.domain.User;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminPointMapper {

    public static AdminPointHistoryDto toDto(PointHistory entity) {
        return AdminPointHistoryDto.builder()
                .orderId(entity.getOrderId())
                .userId(entity.getUser().getId())
                .email(entity.getUser().getEmail())
                .pointAmount(entity.getPointAmount())
                .type(entity.getType())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static AdminPointHistorySummaryDto toSummaryDto(User user, Page<PointHistory> historyPage) {
        int totalCharge = historyPage.stream()
                .filter(h -> h.getType() == PointHistoryType.CHARGE)
                .mapToInt(PointHistory::getPointAmount)
                .sum();

        int totalRefund = historyPage.stream()
                .filter(h -> h.getType() == PointHistoryType.REFUND)
                .mapToInt(PointHistory::getPointAmount)
                .sum();

        Page<PointHistoryResponseDto> dtoPage = historyPage.map(PointHistoryMapper::toDto);

        return AdminPointHistorySummaryDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .totalChargeAmount(totalCharge)
                .totalRefundAmount(totalRefund)
                .histories(dtoPage)
                .build();
    }

    public static AdminPointMonthlyTotalSummaryDto toTotalSummaryDto(int totalCharge, int totalRefund) {
        return AdminPointMonthlyTotalSummaryDto.builder()
                .totalChargeAmount(totalCharge)
                .totalRefundAmount(totalRefund)
                .netProfit(totalCharge - totalRefund)
                .build();
    }


}
