// ✅ 관리자 요양원 CRUD - Controller + Service
// 📁 src/main/java/com/daview/controller/admin/AdminFacilityController.java

package com.daview.controller.admin;

import com.daview.dto.FacilityDTO;
import com.daview.service.admin.AdminFacilityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
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
@RequestMapping("/admin/facilities")
@CrossOrigin(originPatterns = "*", allowCredentials = "true")
public class AdminFacilityController {

    @Autowired
    private AdminFacilityService facilityService;

    // 파일 업로드 설정
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

    // 요양원 등록
    @PostMapping
    public ResponseEntity<String> addFacility(@RequestBody FacilityDTO facilityDTO) {
        try {
            facilityService.addFacility(facilityDTO);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body("요양원이 성공적으로 등록되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("요양원 등록에 실패했습니다: " + e.getMessage());
        }
    }

    // 전체 요양원 목록 조회
    @GetMapping
    public ResponseEntity<List<FacilityDTO>> getAllFacilities() {
        try {
            List<FacilityDTO> facilities = facilityService.getAllFacilities();
            return ResponseEntity.ok(facilities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 특정 요양원 조회
    @GetMapping("/{id}")
    public ResponseEntity<FacilityDTO> getFacilityById(@PathVariable("id") String id) {
        try {
            FacilityDTO facility = facilityService.getFacilityById(id);
            if (facility != null) {
                return ResponseEntity.ok(facility);
            }
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // 요양원 정보 수정
    @PutMapping("/{id}")
    public ResponseEntity<String> updateFacility(@PathVariable("id") String id, @RequestBody FacilityDTO facilityDTO) {
        try {
            facilityService.updateFacility(id, facilityDTO);
            return ResponseEntity.ok("요양원 정보가 성공적으로 수정되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("요양원 정보 수정에 실패했습니다: " + e.getMessage());
        }
    }

    // 요양원 삭제
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteFacility(@PathVariable("id") String id) {
        try {
            facilityService.deleteFacility(id);
            return ResponseEntity.ok("요양원이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("요양원 삭제에 실패했습니다: " + e.getMessage());
        }
    }

    // 지역별 요양원 검색
    @GetMapping("/search")
    public ResponseEntity<List<FacilityDTO>> searchFacilitiesByLocation(
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String facilityType) {
        try {
            List<FacilityDTO> facilities = facilityService.getAllFacilities();
            return ResponseEntity.ok(facilities);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ============= 파일 업로드 관련 메소드 =============

    /**
     * 테스트용 엔드포인트 - 컨트롤러 확인
     */
    @GetMapping("/upload/test")
    public ResponseEntity<Map<String, String>> testUploadEndpoint() {
        Map<String, String> response = new HashMap<>();
        response.put("status", "AdminFacilityController upload working!");
        response.put("timestamp", java.time.LocalDateTime.now().toString());
        System.out.println("✅ AdminFacilityController 업로드 테스트 엔드포인트 호출됨");
        return ResponseEntity.ok(response);
    }

    /**
     * 시설 사진 업로드
     */
    @PostMapping("/upload/photo")
    public ResponseEntity<Map<String, Object>> uploadFacilityPhoto(
            @RequestParam("file") MultipartFile file) {
        
        Map<String, Object> response = new HashMap<>();
        
        try {
            System.out.println("📸 시설 사진 업로드 요청 받음: " + file.getOriginalFilename());
            
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

            System.out.println("📸 파일 업로드 성공: " + savedFileName);
            return ResponseEntity.ok(response);

        } catch (Exception e) {
            System.err.println("❌ 파일 업로드 실패: " + e.getMessage());
            e.printStackTrace();
            
            response.put("success", false);
            response.put("message", "파일 업로드 중 오류가 발생했습니다: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    /**
     * 파일 유효성 검사
     */
    private String validateFile(MultipartFile file) {
        if (file.isEmpty()) {
            return "파일이 선택되지 않았습니다.";
        }

        if (file.getSize() > MAX_FILE_SIZE) {
            return "파일 크기가 10MB를 초과할 수 없습니다.";
        }

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
}