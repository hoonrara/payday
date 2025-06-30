package com.example.payday.user.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

//    @OneToOne(mappedBy = "user")
//    private UserProfile userProfile;

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
            throw new IllegalStateException("포인트 부족");
        }
        this.point -= amount;
    }
}
