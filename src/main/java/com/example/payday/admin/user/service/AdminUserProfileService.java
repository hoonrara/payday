package com.example.payday.admin.user.service;

import com.example.payday.admin.user.dto.AdminUserProfileListResponseDto;
import com.example.payday.admin.user.mapper.AdminUserProfileMapper;
import com.example.payday.user.domain.UserProfile;
import com.example.payday.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AdminUserProfileService {

    private final UserProfileRepository userProfileRepository;

    @Transactional(readOnly = true)
    public Page<AdminUserProfileListResponseDto> getAllProfiles(String sortKey, Pageable pageable) {
        Page<UserProfile> profiles = userProfileRepository.findAllSorted(sortKey, pageable);
        return profiles.map(AdminUserProfileMapper::toDto);
    }

    @Transactional(readOnly = true)
    public AdminUserProfileListResponseDto getUserProfileById(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(() -> new IllegalArgumentException("유저를 찾을 수 없습니다."));
        return AdminUserProfileMapper.toDto(profile);
    }
}
