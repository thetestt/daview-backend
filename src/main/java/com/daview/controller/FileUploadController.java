package com.daview.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:3000")
public class FileUploadController {
    
    // 생성자에서 로딩 확인
    public FileUploadController() {
        System.out.println("🔥 FileUploadController 인스턴스 생성됨!");
    }

    @Value("${file.upload.path:uploads/}")
    private String uploadPath;

    @Value("${file.upload.url:/api/files/}")
    private String fileUrlPattern;

    // 허용된 이미지 확장자
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList(
        "jpg", "jpeg", "png", "gif", "bmp", "webp"
    );

    // 최대 파일 크기 (10MB)
    private static final long MAX_FILE_SIZE = 10 * 1024 * 1024;

    /**
     * 테스트용 엔드포인트 - 컨트롤러 로드 확인
     */
    @GetMapping("/upload/test")
    public String testEndpoint() {
        System.out.println("✅ FileUploadController 테스트 엔드포인트 호출됨");
        return "FileUploadController is working! Time: " + java.time.LocalDateTime.now().toString();
    }

    /**
     * 또 다른 테스트 엔드포인트
     */
    @GetMapping("/test-simple")
    public String testSimple() {
        System.out.println("✅ 간단한 테스트 엔드포인트 호출됨");
        return "Simple test works!";
    }

    /**
     * 시설 사진 업로드
     */
    @PostMapping(value = "/upload/facility-photo", consumes = "multipart/form-data")
    public ResponseEntity<Map<String, Object>> uploadFacilityPhoto(
            @RequestParam("file") MultipartFile file) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            // 파일 유효성 검사
            String validationError = validateFile(file);
            if (validationError != null) {
                response.put("success", false);
                response.put("message", validationError);
                return ResponseEntity.badRequest().body(response);
            }

            // 파일 저장
            String savedFileName = saveFile(file);
            String fileUrl = fileUrlPattern + savedFileName;

            response.put("success", true);
            response.put("message", "파일이 성공적으로 업로드되었습니다.");
            response.put("fileName", savedFileName);
            response.put("fileUrl", fileUrl);
            response.put("originalName", file.getOriginalFilename());
            response.put("fileSize", file.getSize());

            System.out.println("파일 업로드 성공: " + savedFileName);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("파일 업로드 실패: " + e.getMessage());
            e.printStackTrace();
            
            response.put("success", false);
            response.put("message", "파일 업로드 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }

    /**
     * 파일 유효성 검사
     */
    private String validateFile(MultipartFile file) {
        // 파일 존재 확인
        if (file.isEmpty()) {
            return "파일이 선택되지 않았습니다.";
        }

        // 파일 크기 확인
        if (file.getSize() > MAX_FILE_SIZE) {
            return "파일 크기가 10MB를 초과할 수 없습니다.";
        }

        // 파일 확장자 확인
        String originalFilename = file.getOriginalFilename();
        if (originalFilename == null || originalFilename.trim().isEmpty()) {
            return "유효하지 않은 파일명입니다.";
        }

        String extension = getFileExtension(originalFilename).toLowerCase();
        if (!ALLOWED_EXTENSIONS.contains(extension)) {
            return "지원하지 않는 파일 형식입니다. (지원: " + String.join(", ", ALLOWED_EXTENSIONS) + ")";
        }

        return null; // 유효함
    }

    /**
     * 파일 저장
     */
    private String saveFile(MultipartFile file) throws IOException {
        // 업로드 디렉토리 생성
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }

        // 고유한 파일명 생성
        String originalFilename = file.getOriginalFilename();
        String extension = getFileExtension(originalFilename);
        String uniqueFilename = "facility_" + UUID.randomUUID().toString() + "." + extension;

        // 파일 저장
        Path filePath = Paths.get(uploadPath, uniqueFilename);
        Files.write(filePath, file.getBytes());

        return uniqueFilename;
    }

    /**
     * 파일 확장자 추출
     */
    private String getFileExtension(String filename) {
        if (filename == null || !filename.contains(".")) {
            return "";
        }
        return filename.substring(filename.lastIndexOf(".") + 1);
    }

    /**
     * 업로드된 파일 목록 조회 (관리자용)
     */
    @GetMapping("/upload/list")
    public ResponseEntity<Map<String, Object>> getUploadedFiles() {
        Map<String, Object> response = new HashMap<>();
        
        try {
            File uploadDir = new File(uploadPath);
            File[] files = uploadDir.listFiles((dir, name) -> name.startsWith("facility_"));
            
            if (files != null) {
                response.put("success", true);
                response.put("count", files.length);
                response.put("files", Arrays.stream(files)
                    .map(f -> Map.of(
                        "name", f.getName(),
                        "size", f.length(),
                        "url", fileUrlPattern + f.getName()
                    ))
                    .toArray());
            } else {
                response.put("success", true);
                response.put("count", 0);
                response.put("files", new Object[]{});
            }
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "파일 목록 조회 실패: " + e.getMessage());
            return ResponseEntity.status(500).body(response);
        }
    }
} 