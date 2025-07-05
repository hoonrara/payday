package com.example.payday.point.repository;


import com.example.payday.point.domain.PointHistory;
import com.example.payday.point.domain.type.PointHistoryType;
import com.example.payday.user.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PointHistoryRepository extends JpaRepository<PointHistory, Long> {
    Optional<PointHistory> findByOrderIdAndType(String orderId, PointHistoryType type);

    List<PointHistory> findAllByUser(User user);

    Page<PointHistory> findAllByUser(User user, Pageable pageable);


    boolean existsByOrderId(String orderId);

    List<PointHistory> findAllByCreatedAtAfter(LocalDateTime time);

    @EntityGraph(attributePaths = {"user"})
    Page<PointHistory> findAllBy(Pageable pageable);





}
