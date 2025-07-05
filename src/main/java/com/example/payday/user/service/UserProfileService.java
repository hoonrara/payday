package com.example.payday.user.service;


import com.example.payday.coupon.service.CouponIssueService;
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

    @Transactional(readOnly = true)
    public Page<UserProfileListResponseDto> getAllProfiles(String sortKey, Pageable pageable) {
        Page<UserProfile> profiles = userProfileRepository.findAllSorted(sortKey, pageable);
        return profiles.map(UserProfileMapper::toListDto);
    }

    @Transactional
    public UserProfileDetailResponseDto getProfileDetail(Long userId) {
        UserProfile profile = userProfileRepository.findByUser_Id(userId)
                .orElseThrow(UserNotFoundException::new);

        profile.increaseViewCount();

        couponIssueService.issueAutoCoupons(profile.getUser(), profile.getViewCount());

        return UserProfileMapper.toDetailDto(profile);
    }
}
