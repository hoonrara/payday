package com.example.payday.user.mapper;

import com.example.payday.user.domain.User;
import com.example.payday.user.domain.UserProfile;
import com.example.payday.user.dto.UserSignupRequestDto;
import com.example.payday.user.dto.UserSignupResponseDto;

public class UserMapper {
    public static User toEntity(UserSignupRequestDto dto) {
        return User.builder()
                .email(dto.getEmail())
                .password(dto.getPassword())
                .point(0)
                .build();
    }

    public static UserProfile toProfile(User user, String nickname) {
        return UserProfile.builder()
                .user(user)
                .nickname(nickname)
                .build();
    }

    public static UserSignupResponseDto toSignupDto(User user, UserProfile profile) {
        return UserSignupResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(profile.getNickname())
                .message("회원가입이 완료되었습니다.")
                .build();
    }
}