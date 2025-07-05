package com.example.payday.admin.user.controller;

import com.example.payday.admin.user.dto.AdminUserProfileListResponseDto;
import com.example.payday.admin.user.service.AdminUserProfileService;
import com.example.payday.global.dto.PagedResponse;
import com.example.payday.global.mapper.PagedResponseMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin/profiles")
public class AdminUserProfileController {

    private final AdminUserProfileService adminUserProfileService;

    @GetMapping
    public ResponseEntity<PagedResponse<AdminUserProfileListResponseDto>> getAllProfiles(
            @RequestParam(defaultValue = "date") String sortKey,
            Pageable pageable
    ) {
        Page<AdminUserProfileListResponseDto> page = adminUserProfileService.getAllProfiles(sortKey, pageable);
        return ResponseEntity.ok(PagedResponseMapper.toResponse(page));
    }


    @GetMapping("/{userId}")
    public ResponseEntity<AdminUserProfileListResponseDto> getUserProfile(@PathVariable Long userId) {
        return ResponseEntity.ok(adminUserProfileService.getUserProfileById(userId));
    }
}