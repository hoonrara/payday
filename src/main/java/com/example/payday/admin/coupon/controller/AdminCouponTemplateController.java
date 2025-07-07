package com.example.payday.admin.coupon.controller;

import com.example.payday.admin.coupon.dto.AdminCouponTemplateCreateRequestDto;
import com.example.payday.admin.coupon.dto.AdminCouponTemplateResponseDto;
import com.example.payday.admin.coupon.service.AdminCouponTemplateService;
import com.example.payday.global.dto.PagedResponse;
import com.example.payday.global.mapper.PagedResponseMapper;
import com.example.payday.global.exception.base.ErrorResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "B09. [ADMIN] 어드민 쿠폰 템플릿 API", description = "관리자 - 쿠폰 정책(템플릿) 관리 API")
@RestController
@RequestMapping("/admin/coupon-templates")
@RequiredArgsConstructor
public class AdminCouponTemplateController {

    private final AdminCouponTemplateService service;

    @Operation(summary = "쿠폰 템플릿 생성", description = "새로운 쿠폰 템플릿을 등록합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "생성 완료"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 또는 중복된 템플릿",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping
    public ResponseEntity<AdminCouponTemplateResponseDto> create(@RequestBody AdminCouponTemplateCreateRequestDto dto) {
        AdminCouponTemplateResponseDto created = service.create(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(created);
    }

    @Operation(summary = "쿠폰 템플릿 목록 조회", description = "모든 쿠폰 템플릿을 페이지네이션으로 조회합니다.")
    @ApiResponse(responseCode = "200", description = "정상 응답")
    @GetMapping
    public ResponseEntity<PagedResponse<AdminCouponTemplateResponseDto>> getAll(Pageable pageable) {
        Page<AdminCouponTemplateResponseDto> page = service.getAllTemplates(pageable);
        return ResponseEntity.ok(PagedResponseMapper.toResponse(page));
    }
}
