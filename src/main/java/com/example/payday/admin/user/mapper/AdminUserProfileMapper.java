package com.example.payday.admin.user.mapper;

import com.example.payday.admin.user.dto.AdminUserProfileListResponseDto;
import com.example.payday.user.domain.User;
import com.example.payday.user.domain.UserProfile;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class AdminUserProfileMapper {

    public static AdminUserProfileListResponseDto toDto(UserProfile profile) {
        User user = profile.getUser();

        return AdminUserProfileListResponseDto.builder()
                .userId(user.getId())
                .email(user.getEmail())
                .nickname(profile.getNickname())
                .point(user.getPoint())
                .viewCount(profile.getViewCount())
                .joinedAt(profile.getCreatedAt())

                // ⚠️ 아직 연동되지 않은 필드 - 후속 구현 예정
                .lastLoginAt("구현예정")
                .suspendedStatus("구현예정")
                .reportCountText("구현예정")
                .build();
    }
}