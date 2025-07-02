package com.example.payday.user.domain;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nickname;
    private int viewCount;

    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    public UserProfile(String nickname, User user) {
        this.nickname = nickname;
        this.user = user;
        this.viewCount = 0;
        this.createdAt = LocalDateTime.now();
    }

    public void increaseViewCount() {
        this.viewCount++;
    }
}