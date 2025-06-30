package com.example.payday.point.domain;


import com.example.payday.point.domain.type.PointHistoryType;
import com.example.payday.user.domain.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PointHistory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int amount;

    @Enumerated(EnumType.STRING)
    private PointHistoryType type;

    private LocalDateTime createdAt;

    @Column(nullable = false)
    private int currentPoint = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public PointHistory(int amount, PointHistoryType type, User user, int currentPoint) {
        this.amount = amount;
        this.type = type;
        this.user = user;
        this.currentPoint = currentPoint;
        this.createdAt = LocalDateTime.now();
    }



}
