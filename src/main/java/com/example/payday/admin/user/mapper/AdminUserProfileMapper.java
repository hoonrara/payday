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
                .lastLoginAt("구현예정")          // ✅ 문자열 하드코딩
                .suspendedStatus("구현예정")
                .reportCountText("구현예정")
                .build();

    }
}