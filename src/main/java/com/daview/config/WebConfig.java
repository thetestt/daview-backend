package com.daview.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // http://localhost:8080/uploads/파일이름 으로 접근 가능하게 함
        registry.addResourceHandler("/uploads/**")
                .addResourceLocations("file:uploads/"); // ✅ 경로에 꼭 file: 붙이기
    }
}