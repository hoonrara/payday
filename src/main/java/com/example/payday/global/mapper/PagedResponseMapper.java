package com.example.payday.global.mapper;

import com.example.payday.global.dto.PagedResponse;
import org.springframework.data.domain.Page;

public class PagedResponseMapper {

    public static <T> PagedResponse<T> toResponse(Page<T> page) {
        return new PagedResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isLast()
        );
    }
}
