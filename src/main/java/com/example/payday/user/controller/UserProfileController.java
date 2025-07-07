package com.example.payday.user.controller;

import com.example.payday.global.dto.PagedResponse;
import com.example.payday.global.mapper.PagedResponseMapper;
import com.example.payday.user.dto.UserProfileDetailResponseDto;
import com.example.payday.user.dto.UserProfileListResponseDto;
import com.example.payday.user.exception.MissingUserIdForMeEndpointException;
import com.example.payday.user.service.UserProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/profiles")
public class UserProfileController {

    private final UserProfileService userProfileService;

    @GetMapping
    public ResponseEntity<PagedResponse<UserProfileListResponseDto>> getProfiles(
            @RequestParam(defaultValue = "date") String sortKey,
            Pageable pageable
    ) {
        Page<UserProfileListResponseDto> profiles = userProfileService.getAllProfiles(sortKey, pageable);
        return ResponseEntity.ok(PagedResponseMapper.toResponse(profiles));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileDetailResponseDto> getProfileDetail(@PathVariable Long userId) {
        return ResponseEntity.ok(userProfileService.getProfileDetail(userId));
    }

    @GetMapping("/me")
    public ResponseEntity<UserProfileDetailResponseDto> getMyProfile(@RequestParam(required = false) Long userId) {
        if (userId == null) {
            throw new MissingUserIdForMeEndpointException();
        }
        return ResponseEntity.ok(userProfileService.getMyProfile(userId));
    }
}