package com.example.payday.global.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "페이지 응답 래퍼")
public class PagedResponse<T> {

    @Schema(description = "응답 데이터 목록")
    private List<T> content;

    @Schema(description = "현재 페이지 번호", example = "0")
    private int page;

    @Schema(description = "페이지 크기", example = "10")
    private int size;

    @Schema(description = "전체 데이터 수", example = "124")
    private long totalElements;

    @Schema(description = "전체 페이지 수", example = "13")
    private int totalPages;

    @Schema(description = "마지막 페이지 여부", example = "false")
    private boolean last;
}
