package com.example.payday.payment.controller;

import com.example.payday.payment.dto.PaymentResultDto;
import com.example.payday.payment.service.PaymentService;
import com.example.payday.point.dto.PointChargeRequestDto;
import com.example.payday.point.dto.RefundRequestDto;
import com.example.payday.point.dto.RefundResultDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import com.example.payday.global.exception.base.ErrorResponse;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/payments")
@RequiredArgsConstructor
@Tag(name = "A06. [USER] 결제 API", description = "결제 및 환불 관련 API")
public class PaymentController {

    private final PaymentService paymentService;

    @Operation(summary = "포인트 충전", description = "결제 수단을 통해 포인트를 충전합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "충전 성공"),
            @ApiResponse(responseCode = "400", description = "잘못된 요청 또는 결제 실패",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/charge")
    public ResponseEntity<PaymentResultDto> charge(@RequestBody @Validated PointChargeRequestDto request) {
        PaymentResultDto result = paymentService.chargePoint(request);
        return ResponseEntity.ok(result);
    }

    @Operation(summary = "포인트 환불", description = "결제 환불을 진행하고 포인트를 차감합니다.")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "환불 성공"),
            @ApiResponse(responseCode = "400", description = "환불 실패 (이미 환불된 결제, 포인트 부족 등)",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class))),
            @ApiResponse(responseCode = "404", description = "환불 대상 결제 정보 없음",
                    content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
    })
    @PostMapping("/refund")
    public ResponseEntity<RefundResultDto> refund(@RequestBody @Validated RefundRequestDto request) {
        RefundResultDto result = paymentService.refundPoint(request);
        return ResponseEntity.ok(result);
    }

}