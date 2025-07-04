package com.example.payday.user.controller;

import com.example.payday.user.dto.UserProfileDetailResponseDto;
import com.example.payday.user.dto.UserProfileListResponseDto;
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
    public ResponseEntity<Page<UserProfileListResponseDto>> getProfiles(
            @RequestParam(defaultValue = "date") String sortKey,
            Pageable pageable
    ) {
        return ResponseEntity.ok(userProfileService.getAllProfiles(sortKey, pageable));
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserProfileDetailResponseDto> getProfileDetail(@PathVariable Long userId) {
        return ResponseEntity.ok(userProfileService.getProfileDetail(userId));
    }
}