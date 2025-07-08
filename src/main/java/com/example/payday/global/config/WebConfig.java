package com.example.payday.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> resolvers) {
        PageableHandlerMethodArgumentResolver resolver = new PageableHandlerMethodArgumentResolver();

        // 전역 기본 페이지 설정: page=0, size=10
        resolver.setFallbackPageable(org.springframework.data.domain.PageRequest.of(0, 10));

        // 최대 페이지 크기 제한
        resolver.setMaxPageSize(100);

        resolvers.add(resolver);
    }
}