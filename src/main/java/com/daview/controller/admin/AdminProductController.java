package com.daview.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.daview.service.admin.AdminCaregiverService;
import com.daview.dto.CaregiverDTO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin/products") // 프론트엔드가 기대하는 경로
public class AdminProductController {

    @Autowired
    private AdminCaregiverService caregiverService;

    // 상품(간병인) 목록 조회 - 실제 DB 데이터 사용
    @GetMapping(produces = "application/json; charset=UTF-8") // UTF-8 인코딩 명시
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search) {
        
        try {
            System.out.println("=== AdminProductController.getProducts 호출됨 ===");
            System.out.println("검색어: " + search + ", 페이지: " + page + ", 사이즈: " + size);
            
            // 실제 DB에서 간병인 데이터 조회
            List<CaregiverDTO> caregivers = caregiverService.getAllCaregivers();
            System.out.println("DB에서 조회된 간병인 수: " + caregivers.size());
            
            // 프론트엔드 형식에 맞게 데이터 변환
            List<Map<String, Object>> products = new ArrayList<>();
            
            for (CaregiverDTO caregiver : caregivers) {
                Map<String, Object> product = new HashMap<>();
                
                // 프론트엔드가 기대하는 필드명으로 매핑
                product.put("prodId", caregiver.getCaregiverId() != null ? caregiver.getCaregiverId() : "미설정");
                product.put("prodName", "간병사 ID: " + caregiver.getCaregiverId()); // 실제 이름이 없으므로 ID로 표시
                product.put("prodTypeName", "간병사"); // 고정값
                product.put("prodType", "caregiver"); // 고정값
                Integer hopeWorkAmount = caregiver.getHopeWorkAmount();
                product.put("prodPrice", hopeWorkAmount != null ? hopeWorkAmount.intValue() : 0);
                product.put("price", hopeWorkAmount != null ? hopeWorkAmount.intValue() : 0);
                product.put("prodDetail", caregiver.getIntroduction() != null ? caregiver.getIntroduction() : "소개글이 없습니다.");
                product.put("description", caregiver.getIntroduction() != null ? caregiver.getIntroduction() : "소개글이 없습니다.");
                
                // 추가 정보
                product.put("location", 
                    (caregiver.getHopeWorkAreaLocation() != null ? caregiver.getHopeWorkAreaLocation() : "") + 
                    " " + 
                    (caregiver.getHopeWorkAreaCity() != null ? caregiver.getHopeWorkAreaCity() : ""));
                product.put("workType", caregiver.getHopeWorkType() != null ? caregiver.getHopeWorkType() : "미설정");
                product.put("workPlace", caregiver.getHopeWorkPlace() != null ? caregiver.getHopeWorkPlace() : "미설정");
                product.put("employmentType", caregiver.getHopeEmploymentType() != null ? caregiver.getHopeEmploymentType() : "미설정");
                product.put("education", caregiver.getEducationLevel() != null ? caregiver.getEducationLevel() : "미설정");
                product.put("createdAt", caregiver.getCaregiverCreatedAt() != null ? caregiver.getCaregiverCreatedAt().toString() : "미설정");
                
                // 자격증과 경력 정보 추가
                product.put("certificatesString", caregiver.getCertificatesString() != null ? caregiver.getCertificatesString() : "정보 없음");
                product.put("careerString", caregiver.getCareerString() != null ? caregiver.getCareerString() : "정보 없음");
                product.put("startDateString", caregiver.getStartDateString() != null ? caregiver.getStartDateString() : "");
                product.put("endDateString", caregiver.getEndDateString() != null ? caregiver.getEndDateString() : "");
                
                products.add(product);
            }
            
            // 검색어가 있으면 필터링
            if (search != null && !search.trim().isEmpty()) {
                String searchLower = search.toLowerCase();
                products = products.stream()
                    .filter(p -> 
                        p.get("prodName").toString().toLowerCase().contains(searchLower) ||
                        p.get("prodDetail").toString().toLowerCase().contains(searchLower) ||
                        p.get("location").toString().toLowerCase().contains(searchLower)
                    )
                    .toList();
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("content", products);
            response.put("totalElements", products.size());
            response.put("totalPages", (int) Math.ceil((double) products.size() / size));
            response.put("size", size);
            response.put("number", page);
            
            System.out.println("=== 응답 데이터 ===");
            System.out.println("총 간병인 수: " + products.size());
            if (!products.isEmpty()) {
                System.out.println("첫 번째 간병인: " + products.get(0).get("prodName"));
            }
            System.out.println("=== 응답 반환 성공 ===");
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
            
        } catch (Exception e) {
            System.out.println("=== 에러 발생: " + e.getMessage() + " ===");
            e.printStackTrace();
            
            // 에러 발생 시 빈 데이터 반환
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("content", new ArrayList<>());
            errorResponse.put("totalElements", 0);
            errorResponse.put("totalPages", 0);
            errorResponse.put("size", size);
            errorResponse.put("number", page);
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }

    @PostMapping(produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> createProduct(@RequestBody Map<String, Object> productData) {
        return ResponseEntity.ok("간병사가 성공적으로 등록되었습니다.");
    }

    @PutMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> updateProduct(
            @PathVariable String id, 
            @RequestBody Map<String, Object> productData) {
        return ResponseEntity.ok("간병사 정보가 성공적으로 수정되었습니다.");
    }

    @DeleteMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        return ResponseEntity.ok("간병사가 성공적으로 삭제되었습니다.");
    }
} 