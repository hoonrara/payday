package com.example.payday.user.domain;

import com.example.payday.point.exception.InsufficientPointException;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    private int point;

    @Version
    private Long version; // 낙관적 락용 버전 필드

    // 관리자용 정보 (마이페이지에 노출되지 않음)
    @Column(name = "last_login_at")
    private LocalDateTime lastLoginAt;

    @Column(name = "is_suspended")
    private boolean isSuspended = false;

    @Column(name = "report_count")
    private int reportCount = 0;

    @Builder
    public User(String email, String password, int point) {
        this.email = email;
        this.password = password;
        this.point = point;
    }

    public void addPoint(int amount) {
        this.point += amount;
    }

    public void usePoint(int amount) {
        if (this.point < amount) {
            throw new InsufficientPointException();
        }
        this.point -= amount;
    }
}

