package com.daview;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@MapperScan("com.daview.mapper")  // ✅ 매퍼 패키지 경로를 정확히 명시
@SpringBootApplication
public class DaviewBackendApplication {
    public static void main(String[] args) {
        SpringApplication.run(DaviewBackendApplication.class, args);
    }
}
