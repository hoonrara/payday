package com.example.payday.admin.user.service;

import com.example.payday.admin.user.dto.AdminUserProfileListResponseDto;
import com.example.payday.admin.user.mapper.AdminUserProfileMapper;
import com.example.payday.user.domain.UserProfile;
import com.example.payday.user.exception.UserNotFoundException;
import com.example.payday.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AdminUserProfileService {

    private final UserProfileRepository userProfileRepository;

    public Page<AdminUserProfileListResponseDto> getAllProfiles(String sortKey, Pageable pageable) {
        Page<UserProfile> profiles = userProfileRepository.findAllSorted(sortKey, pageable);
        return profiles.map(AdminUserProfileMapper::toDto);
    }


    public AdminUserProfileListResponseDto getUserProfileById(Long userId) {
        UserProfile profile = userProfileRepository.findByUserId(userId)
                .orElseThrow(UserNotFoundException::new);
        return AdminUserProfileMapper.toDto(profile);
    }
}
