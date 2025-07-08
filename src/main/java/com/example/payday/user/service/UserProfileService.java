package com.example.payday.user.service;


import com.example.payday.coupon.service.CouponIssueService;
import com.example.payday.user.domain.User;
import com.example.payday.user.domain.UserProfile;
import com.example.payday.user.dto.UserProfileDetailResponseDto;
import com.example.payday.user.dto.UserProfileListResponseDto;
import com.example.payday.user.exception.UserNotFoundException;
import com.example.payday.user.mapper.UserProfileMapper;
import com.example.payday.user.repository.UserProfileRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;



@Service
@RequiredArgsConstructor
public class UserProfileService {

    private final UserProfileRepository userProfileRepository;
    private final CouponIssueService couponIssueService;

    /**
     * 전체 사용자 프로필 목록 조회 (정렬 기준 포함)
     */
    @Transactional(readOnly = true)
    public Page<UserProfileListResponseDto> getAllProfiles(String sortKey, Pageable pageable) {
        Page<UserProfile> profiles = userProfileRepository.findAllSorted(sortKey, pageable);
        return profiles.map(UserProfileMapper::toListDto);
    }

    /**
     * 특정 사용자 프로필 상세 조회
     * - 조회수 증가
     * - 자동 쿠폰 발급 트리거
     */
    @Transactional
    public UserProfileDetailResponseDto getProfileDetail(Long userId) {
        UserProfile profile = userProfileRepository.findWithUserByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        profile.increaseViewCount();
        couponIssueService.issueAutoCoupons(profile.getUser(), profile.getViewCount());

        return UserProfileMapper.toDetailDto(profile);
    }

    /**
     * 본인 프로필 조회 (조회수 증가 없음)
     */
    @Transactional(readOnly = true)
    public UserProfileDetailResponseDto getMyProfile(Long userId) {
        UserProfile profile = userProfileRepository.findWithUserByUserId(userId)
                .orElseThrow(UserNotFoundException::new);

        return UserProfileMapper.toDetailDto(profile);
    }
}
