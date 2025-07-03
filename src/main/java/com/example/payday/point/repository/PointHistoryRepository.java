package com.example.payday.point.repository;


import com.example.payday.point.domain.PointHistory;
import com.example.payday.point.domain.type.PointHistoryType;
import com.example.payday.user.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    Optional<PointHistory> findByOrderIdAndType(String orderId, PointHistoryType type);

    List<PointHistory> findAllByUser(User user);

    boolean existsByOrderId(String orderId);


}
