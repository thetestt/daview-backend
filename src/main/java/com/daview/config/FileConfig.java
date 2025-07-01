package com.daview.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class FileConfig implements WebMvcConfigurer {

    @Value("${file.upload.path:uploads/}")
    private String uploadPath;

    @Value("${file.upload.url:/api/files/}")
    private String fileUrlPattern;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 업로드된 파일을 웹에서 접근할 수 있도록 정적 리소스 매핑
        registry.addResourceHandler(fileUrlPattern + "**")
                .addResourceLocations("file:" + uploadPath);
        
        System.out.println("파일 리소스 매핑 설정:");
        System.out.println("  URL 패턴: " + fileUrlPattern + "**");
        System.out.println("  파일 경로: file:" + uploadPath);
    }
} 