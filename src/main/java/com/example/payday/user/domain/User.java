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

    private String email;
    private String password;
    private int point;

    @Version
    private Long version;


    //관리자만 볼 수 있음 (로직은 추후 구현예정)
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

    // 포인트 증가
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
