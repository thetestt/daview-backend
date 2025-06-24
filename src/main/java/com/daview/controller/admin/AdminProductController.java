package com.daview.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.daview.service.admin.AdminCaregiverService;
import com.daview.dto.CaregiverDTO;
import com.daview.mapper.UserMapper;
import com.daview.dto.User;

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
    
    @Autowired
    private UserMapper userMapper;

    // 상품(간병인) 목록 조회 - 실제 DB 데이터 사용
    @GetMapping(produces = "application/json; charset=UTF-8") // UTF-8 인코딩 명시
    public ResponseEntity<Map<String, Object>> getProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "") String type) {
        
        try {
            System.out.println("=== AdminProductController.getProducts 호출됨 ===");
            System.out.println("검색어: " + search + ", 페이지: " + page + ", 사이즈: " + size + ", 타입: " + type);
            
            // ⭐ 수정: type 파라미터에 따라 데이터 필터링
            List<Map<String, Object>> products = new ArrayList<>();
            
            // 요양원/실버타운 요청 시 빈 결과 반환 (현재 DB에 해당 데이터 없음)
            if ("요양원/실버타운".equals(type)) {
                System.out.println("요양원/실버타운 필터링 - 빈 결과 반환");
                // 빈 리스트 반환
            } else {
                // 기본값 또는 요양사 타입인 경우 간병인 데이터 조회
                List<CaregiverDTO> caregivers = caregiverService.getAllCaregivers();
                System.out.println("DB에서 조회된 간병인 수: " + caregivers.size());
            
                for (CaregiverDTO caregiver : caregivers) {
                    Map<String, Object> product = new HashMap<>();
                    
                    // 프론트엔드가 기대하는 필드명으로 매핑
                    product.put("prodId", caregiver.getCaregiverId() != null ? caregiver.getCaregiverId() : "미설정");
                    product.put("prodName", caregiver.getUsername() != null ? caregiver.getUsername() : ("간병사 ID: " + caregiver.getCaregiverId())); // 실제 사용자명 사용
                    product.put("prodTypeName", "요양사"); // 고정값 (간병사 -> 요양사로 수정)
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
                    
                    // 사용자 정보 추가
                    product.put("username", caregiver.getUsername() != null ? caregiver.getUsername() : "미설정");
                    product.put("userGender", caregiver.getUserGender() != null ? caregiver.getUserGender() : "미설정");
                    
                    products.add(product);
                }
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
    public ResponseEntity<Map<String, Object>> createProduct(@RequestBody Map<String, Object> productData) {
        try {
            System.out.println("=== 새로운 상품 등록 요청 시작 ===");
            System.out.println("받은 데이터: " + productData);
            
            // 요양사만 등록 가능하도록 제한
            String prodTypeName = (String) productData.get("prodTypeName");
            if (!"요양사".equals(prodTypeName)) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "현재는 요양사만 등록 가능합니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // 필수 필드 검증
            String prodName = (String) productData.get("prodName");
            Object hopeWorkAmount = productData.get("hope_work_amount");
            
            if (prodName == null || prodName.trim().isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "상품명은 필수 입력 항목입니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            if (hopeWorkAmount == null) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "희망급여는 필수 입력 항목입니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // TODO: 실제로는 현재 로그인한 관리자가 등록하려는 사용자의 member_id를 확인해야 함
            // 임시로 테스트용 member_id 추가 (실제 구현에서는 사용자가 선택하거나 입력해야 함)
            if (!productData.containsKey("member_id")) {
                // 예시: 기존 DB에 있는 member_id 중 하나를 사용 (실제로는 프론트엔드에서 선택하도록 해야 함)
                productData.put("member_id", 1L); // 테스트용 기본값
            }
            
            // 서비스 호출하여 실제 DB에 등록
            caregiverService.createCaregiverProduct(productData);
            
            Map<String, Object> successResponse = new HashMap<>();
            successResponse.put("success", true);
            successResponse.put("message", "요양사가 성공적으로 등록되었습니다.");
            
            System.out.println("=== 상품 등록 완료 ===");
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(successResponse);
            
        } catch (Exception e) {
            System.err.println("상품 등록 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "상품 등록에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
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
    
    // 회원 목록 조회 API (실제 DB 연동) - Alternative Path
    @GetMapping(value = "/get-users", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getUsersAlternative() {
        try {
            System.out.println("=== 실제 DB에서 회원 목록 조회 요청 (Alternative) ===");
            
            // UserMapper null 체크
            if (userMapper == null) {
                System.err.println("ERROR: userMapper가 null입니다!");
                throw new RuntimeException("UserMapper가 주입되지 않았습니다.");
            }
            
            System.out.println("UserMapper 정상 주입됨");
            
            // 실제 DB에서 CAREGIVER 역할 사용자 조회
            List<User> caregiverUsers = userMapper.findUsersByRole("CAREGIVER");
            System.out.println("DB에서 조회된 CAREGIVER 사용자 수: " + caregiverUsers.size());
            
            List<Map<String, Object>> users = new ArrayList<>();
            
            for (User user : caregiverUsers) {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("member_id", user.getMemberId());
                userMap.put("username", user.getUsername());
                userMap.put("name", user.getName());
                userMap.put("email", user.getEmail());
                userMap.put("phone", user.getPhone());
                userMap.put("suggested_product_name", "요양사 " + user.getName()); // 추천 상품명 추가
                users.add(userMap);
                
                System.out.println("사용자: " + user.getName() + " (" + user.getUsername() + ")");
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("users", users);
            
            System.out.println("=== 실제 DB 조회 완료 (Alternative): " + users.size() + "명 ===");
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("실제 DB 회원 목록 조회 중 오류 발생 (Alternative): " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "실제 DB 회원 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }
    
    // 회원 목록 조회 API (실제 DB 연동) - Updated
    @GetMapping(value = "/users", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getUsers() {
        try {
            System.out.println("=== 실제 DB에서 회원 목록 조회 요청 ===");
            
            // 실제 DB에서 CAREGIVER 역할 사용자 조회
            List<User> caregiverUsers = userMapper.findUsersByRole("CAREGIVER");
            System.out.println("DB에서 조회된 CAREGIVER 사용자 수: " + caregiverUsers.size());
            
            List<Map<String, Object>> users = new ArrayList<>();
            
            for (User user : caregiverUsers) {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("member_id", user.getMemberId());
                userMap.put("username", user.getUsername());
                userMap.put("name", user.getName());
                userMap.put("email", user.getEmail());
                userMap.put("phone", user.getPhone());
                userMap.put("suggested_product_name", "요양사 " + user.getName()); // 추천 상품명 추가
                users.add(userMap);
                
                System.out.println("사용자: " + user.getName() + " (" + user.getUsername() + ")");
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("users", users);
            
            System.out.println("=== 실제 DB 조회 완료: " + users.size() + "명 ===");
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
                
        } catch (Exception e) {
            System.err.println("실제 DB 회원 목록 조회 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            
            Map<String, Object> errorResponse = new HashMap<>();
            errorResponse.put("success", false);
            errorResponse.put("message", "실제 DB 회원 목록 조회에 실패했습니다: " + e.getMessage());
            
            return ResponseEntity.status(500)
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(errorResponse);
        }
    }
} 