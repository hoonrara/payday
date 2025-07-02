package com.example.payday.point.repository;


import com.example.payday.point.domain.PointHistory;
import com.example.payday.point.domain.type.PointHistoryType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    Optional<PointHistory> findByOrderIdAndType(String orderId, PointHistoryType type);

}
