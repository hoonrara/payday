package com.example.payday.user.mapper;

import com.example.payday.user.domain.UserProfile;
import com.example.payday.user.dto.UserProfileDetailResponseDto;
import com.example.payday.user.dto.UserProfileListResponseDto;

public class UserProfileMapper {

    public static UserProfileListResponseDto toListDto(UserProfile entity) {
        return UserProfileListResponseDto.builder()
                .nickname(entity.getNickname())
                .viewCount(entity.getViewCount())
                .createdAt(entity.getCreatedAt())
                .build();
    }

    public static UserProfileDetailResponseDto toDetailDto(UserProfile entity) {
        return UserProfileDetailResponseDto.builder()
                .nickname(entity.getNickname())
                .viewCount(entity.getViewCount())
                .createdAt(entity.getCreatedAt())
                .build();
    }
}