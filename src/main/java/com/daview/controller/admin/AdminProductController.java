package com.daview.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.daview.service.admin.AdminCaregiverService;
import com.daview.service.admin.AdminFacilityService;
import com.daview.dto.CaregiverDTO;
import com.daview.dto.FacilityDTO;
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
    private AdminFacilityService facilityService;
    
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
            // System.out.println("=== AdminProductController.getProducts 호출됨 ===");
            // System.out.println("검색어: " + search + ", 페이지: " + page + ", 사이즈: " + size + ", 타입: " + type);
            
            // ⭐ 수정: type 파라미터에 따라 데이터 필터링
            List<Map<String, Object>> products = new ArrayList<>();
            
            // 요양원/실버타운 요청 시 실제 요양원 데이터 조회
            if ("요양원/실버타운".equals(type)) {
                // System.out.println("요양원/실버타운 필터링 - 요양원 데이터 조회");
                List<FacilityDTO> facilities = facilityService.getAllFacilities();
                // System.out.println("DB에서 조회된 요양원 수: " + facilities.size());
                
                for (FacilityDTO facility : facilities) {
                    Map<String, Object> product = new HashMap<>();
                    
                    // 기본 정보 - 프론트엔드가 기대하는 필드명으로 매핑
                    product.put("prodId", facility.getFacilityId() != null ? facility.getFacilityId() : "미설정");
                    product.put("facilityId", facility.getFacilityId() != null ? facility.getFacilityId() : "미설정"); // 추가
                    
                    product.put("prodName", facility.getFacilityName() != null ? facility.getFacilityName() : "시설명 없음");
                    product.put("facilityName", facility.getFacilityName() != null ? facility.getFacilityName() : "시설명 없음"); // 추가
                    
                    product.put("prodTypeName", facility.getFacilityType() != null ? facility.getFacilityType() : "요양원/실버타운");
                    product.put("facilityType", facility.getFacilityType() != null ? facility.getFacilityType() : "요양원/실버타운"); // 추가
                    
                    // 가격 정보
                    Integer charge = facility.getFacilityCharge() != null ? facility.getFacilityCharge() : 
                                   (facility.getMonthlyFee() != null ? facility.getMonthlyFee() : 0);
                    product.put("monthlyFee", charge);
                    product.put("facilityCharge", charge);
                    product.put("prodPrice", charge);
                    product.put("price", charge);
                    
                    // 주소 정보 - 프론트엔드 필드명에 맞춰 매핑
                    product.put("facilityAddressLocation", facility.getFacilityAddressLocation() != null ? facility.getFacilityAddressLocation() : "미설정");
                    product.put("location", facility.getFacilityAddressLocation() != null ? facility.getFacilityAddressLocation() : "미설정");
                    
                    product.put("facilityAddressCity", facility.getFacilityAddressCity() != null ? facility.getFacilityAddressCity() : "미설정");
                    product.put("city", facility.getFacilityAddressCity() != null ? facility.getFacilityAddressCity() : "미설정");
                    
                    product.put("facilityDetailAddress", facility.getFacilityDetailAddress() != null ? facility.getFacilityDetailAddress() : "주소 미설정");
                    product.put("address", facility.getFacilityDetailAddress() != null ? facility.getFacilityDetailAddress() : "주소 미설정");
                    
                    // 연락처 및 홈페이지
                    product.put("facilityPhone", facility.getFacilityPhone() != null ? facility.getFacilityPhone() : "연락처 미설정");
                    product.put("phoneNumber", facility.getFacilityPhone() != null ? facility.getFacilityPhone() : "연락처 미설정");
                    
                    product.put("facilityHomepage", facility.getFacilityHomepage() != null ? facility.getFacilityHomepage() : "");
                    product.put("homepage", facility.getFacilityHomepage() != null ? facility.getFacilityHomepage() : "");
                    
                    // 테마
                    product.put("facilityTheme", facility.getFacilityTheme() != null ? facility.getFacilityTheme() : "테마 미설정");
                    product.put("theme", facility.getFacilityTheme() != null ? facility.getFacilityTheme() : "테마 미설정");
                    
                    // 사진 및 썸네일
                    product.put("photoUrl", facility.getPhotoUrl() != null ? facility.getPhotoUrl() : "");
                    product.put("isThumbnail", facility.getIsThumbnail() != null ? facility.getIsThumbnail() : "일반");
                    
                    // 카테고리와 태그
                    product.put("category", facility.getCategory() != null ? facility.getCategory() : "미설정");
                    product.put("facilityTag", facility.getFacilityTag() != null ? facility.getFacilityTag() : "태그 없음");
                    
                    // 기타
                    product.put("prodType", "facility");
                    product.put("prodDetail", facility.getDefaultMessage() != null ? facility.getDefaultMessage() : "시설 소개가 없습니다.");
                    product.put("description", facility.getDefaultMessage() != null ? facility.getDefaultMessage() : "시설 소개가 없습니다.");
                    product.put("createdAt", facility.getFacilityCreatedAt() != null ? facility.getFacilityCreatedAt() : "미설정");
                    
                    products.add(product);
                }
            } else {
                // 기본값 또는 요양사 타입인 경우 간병인 데이터 조회
                List<CaregiverDTO> caregivers = caregiverService.getAllCaregivers();
                // System.out.println("DB에서 조회된 간병인 수: " + caregivers.size());
            
                for (CaregiverDTO caregiver : caregivers) {
                    Map<String, Object> product = new HashMap<>();
                    
                    // 기본 정보 - 프론트엔드가 기대하는 필드명으로 매핑
                    product.put("prodId", caregiver.getCaregiverId() != null ? caregiver.getCaregiverId() : "미설정");
                    product.put("caregiverId", caregiver.getCaregiverId() != null ? caregiver.getCaregiverId() : "미설정"); // 추가
                    
                    product.put("prodName", caregiver.getUsername() != null ? caregiver.getUsername() : ("간병사 ID: " + caregiver.getCaregiverId()));
                    product.put("username", caregiver.getUsername() != null ? caregiver.getUsername() : "미설정"); // 추가
                    
                    product.put("prodTypeName", "요양사");
                    product.put("prodType", "caregiver");
                    
                    // 희망급여 정보
                    Integer hopeWorkAmount = caregiver.getHopeWorkAmount();
                    product.put("hopeWorkAmount", hopeWorkAmount != null ? hopeWorkAmount.intValue() : 0); // 프론트엔드 필드명
                    product.put("prodPrice", hopeWorkAmount != null ? hopeWorkAmount.intValue() : 0);
                    product.put("price", hopeWorkAmount != null ? hopeWorkAmount.intValue() : 0);
                    
                    // 소개글
                    product.put("introduction", caregiver.getIntroduction() != null ? caregiver.getIntroduction() : "소개글이 없습니다."); // 프론트엔드 필드명
                    product.put("prodDetail", caregiver.getIntroduction() != null ? caregiver.getIntroduction() : "소개글이 없습니다.");
                    product.put("description", caregiver.getIntroduction() != null ? caregiver.getIntroduction() : "소개글이 없습니다.");
                    
                    // 희망근무지역 정보  
                    product.put("hopeWorkAreaLocation", caregiver.getHopeWorkAreaLocation() != null ? caregiver.getHopeWorkAreaLocation() : "미설정"); // 프론트엔드 필드명
                    product.put("hopeWorkAreaCity", caregiver.getHopeWorkAreaCity() != null ? caregiver.getHopeWorkAreaCity() : "미설정"); // 프론트엔드 필드명
                    product.put("location", 
                        (caregiver.getHopeWorkAreaLocation() != null ? caregiver.getHopeWorkAreaLocation() : "") + 
                        " " + 
                        (caregiver.getHopeWorkAreaCity() != null ? caregiver.getHopeWorkAreaCity() : ""));
                    
                    // 근무 정보
                    product.put("hopeWorkPlace", caregiver.getHopeWorkPlace() != null ? caregiver.getHopeWorkPlace() : "미설정"); // 프론트엔드 필드명
                    product.put("workPlace", caregiver.getHopeWorkPlace() != null ? caregiver.getHopeWorkPlace() : "미설정");
                    
                    product.put("hopeWorkType", caregiver.getHopeWorkType() != null ? caregiver.getHopeWorkType() : "미설정"); // 프론트엔드 필드명
                    product.put("workType", caregiver.getHopeWorkType() != null ? caregiver.getHopeWorkType() : "미설정");
                    
                    product.put("hopeEmploymentType", caregiver.getHopeEmploymentType() != null ? caregiver.getHopeEmploymentType() : "미설정"); // 프론트엔드 필드명
                    product.put("employmentType", caregiver.getHopeEmploymentType() != null ? caregiver.getHopeEmploymentType() : "미설정");
                    
                    // 학력
                    product.put("educationLevel", caregiver.getEducationLevel() != null ? caregiver.getEducationLevel() : "미설정"); // 프론트엔드 필드명
                    product.put("education", caregiver.getEducationLevel() != null ? caregiver.getEducationLevel() : "미설정");
                    
                    // 경력 정보
                    product.put("careerString", caregiver.getCareerString() != null ? caregiver.getCareerString() : "정보 없음"); // 프론트엔드 필드명
                    product.put("startDateString", caregiver.getStartDateString() != null ? caregiver.getStartDateString() : ""); // 프론트엔드 필드명
                    product.put("endDateString", caregiver.getEndDateString() != null ? caregiver.getEndDateString() : ""); // 프론트엔드 필드명
                    
                    // 자격증 정보
                    product.put("certificatesString", caregiver.getCertificatesString() != null ? caregiver.getCertificatesString() : "정보 없음"); // 프론트엔드 필드명
                    
                    // 생성/수정 날짜
                    product.put("caregiverCreatedAt", caregiver.getCaregiverCreatedAt() != null ? caregiver.getCaregiverCreatedAt().toString() : "미설정"); // 프론트엔드 필드명
                    product.put("caregiverUpdateAt", caregiver.getCaregiverUpdateAt() != null ? caregiver.getCaregiverUpdateAt().toString() : "미설정"); // 프론트엔드 필드명
                    product.put("createdAt", caregiver.getCaregiverCreatedAt() != null ? caregiver.getCaregiverCreatedAt().toString() : "미설정");
                    
                    // 사용자 정보
                    product.put("userGender", caregiver.getUserGender() != null ? caregiver.getUserGender() : "미설정"); // 프론트엔드 필드명
                    
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
            
            // 전체 데이터 수 저장
            int totalElements = products.size();
            int totalPages = (int) Math.ceil((double) totalElements / size);
            
            // 페이지네이션 적용
            int startIndex = page * size;
            int endIndex = Math.min(startIndex + size, totalElements);
            
            List<Map<String, Object>> pagedProducts = new ArrayList<>();
            if (startIndex < totalElements) {
                pagedProducts = products.subList(startIndex, endIndex);
            }
            
            // System.out.println("=== 페이지네이션 정보 ===");
            // System.out.println("전체 데이터 수: " + totalElements);
            // System.out.println("현재 페이지: " + page + " (0부터 시작)");
            // System.out.println("페이지 크기: " + size);
            // System.out.println("전체 페이지 수: " + totalPages);
            // System.out.println("시작 인덱스: " + startIndex);
            // System.out.println("끝 인덱스: " + endIndex);
            // System.out.println("현재 페이지 데이터 수: " + pagedProducts.size());
            
            Map<String, Object> response = new HashMap<>();
            response.put("content", pagedProducts);
            response.put("totalElements", totalElements);
            response.put("totalPages", totalPages);
            response.put("size", size);
            response.put("number", page);
            response.put("first", page == 0);
            response.put("last", page >= totalPages - 1);
            response.put("numberOfElements", pagedProducts.size());
            
            // System.out.println("=== 응답 데이터 ===");
            // System.out.println("총 간병인 수: " + products.size());
            // if (!products.isEmpty()) {
            //     System.out.println("첫 번째 간병인: " + products.get(0).get("prodName"));
            // }
            // System.out.println("=== 응답 반환 성공 ===");
            
            return ResponseEntity.ok()
                .header("Content-Type", "application/json; charset=UTF-8")
                .body(response);
            
        } catch (Exception e) {
            System.err.println("=== AdminProductController 에러 발생: " + e.getMessage() + " ===");
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
            
            String prodTypeName = (String) productData.get("prodTypeName");
            System.out.println("상품 유형: " + prodTypeName);
            
            // 필수 필드 검증
            String prodName = (String) productData.get("prodName");
            if (prodName == null || prodName.trim().isEmpty()) {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "상품명은 필수 입력 항목입니다.");
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
            // 요양사 등록 처리
            if ("요양사".equals(prodTypeName)) {
                Object hopeWorkAmount = productData.get("hope_work_amount");
                if (hopeWorkAmount == null) {
                    Map<String, Object> errorResponse = new HashMap<>();
                    errorResponse.put("success", false);
                    errorResponse.put("message", "희망급여는 필수 입력 항목입니다.");
                    return ResponseEntity.badRequest().body(errorResponse);
                }
                
                if (!productData.containsKey("member_id")) {
                    productData.put("member_id", 1L); // 테스트용 기본값
                }
                
                // 서비스 호출하여 실제 DB에 등록
                caregiverService.createCaregiverProduct(productData);
                
                Map<String, Object> successResponse = new HashMap<>();
                successResponse.put("success", true);
                successResponse.put("message", "요양사가 성공적으로 등록되었습니다.");
                
                return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(successResponse);
                    
            } 
            // 기업(시설) 등록 처리
            else if ("기업".equals(prodTypeName)) {
                System.out.println("=== 기업 등록 처리 시작 ===");
                
                // 기업 필수 필드 검증
                String facilityName = (String) productData.get("facility_name");
                String facilityType = (String) productData.get("facility_type");
                
                if (facilityName == null || facilityName.trim().isEmpty()) {
                    Map<String, Object> errorResponse = new HashMap<>();
                    errorResponse.put("success", false);
                    errorResponse.put("message", "시설명은 필수 입력 항목입니다.");
                    return ResponseEntity.badRequest().body(errorResponse);
                }
                
                if (facilityType == null || facilityType.trim().isEmpty()) {
                    Map<String, Object> errorResponse = new HashMap<>();
                    errorResponse.put("success", false);
                    errorResponse.put("message", "시설 유형은 필수 입력 항목입니다.");
                    return ResponseEntity.badRequest().body(errorResponse);
                }
                
                // Map 데이터를 FacilityDTO로 변환
                FacilityDTO facilityDTO = new FacilityDTO();
                
                // member_id 설정 (필수!)
                Object memberIdObj = productData.get("member_id");
                if (memberIdObj != null) {
                    try {
                        Long memberId = Long.valueOf(memberIdObj.toString());
                        facilityDTO.setMemberId(memberId);
                        System.out.println("member_id 설정됨: " + memberId);
                    } catch (NumberFormatException e) {
                        System.err.println("member_id 형변환 오류: " + memberIdObj);
                        throw new RuntimeException("올바르지 않은 member_id 값입니다.");
                    }
                } else {
                    throw new RuntimeException("member_id가 누락되었습니다.");
                }
                
                facilityDTO.setFacilityName(facilityName);
                facilityDTO.setFacilityType(facilityType);
                
                // 안전한 형변환 - DB의 facility_charge 컬럼에 매핑
                Object facilityChargeObj = productData.get("facility_charge");
                if (facilityChargeObj != null && !facilityChargeObj.toString().trim().isEmpty()) {
                    try {
                        facilityDTO.setFacilityCharge(Integer.valueOf(facilityChargeObj.toString()));
                    } catch (NumberFormatException e) {
                        System.err.println("facility_charge 형변환 오류: " + facilityChargeObj);
                        facilityDTO.setFacilityCharge(0); // 기본값
                    }
                }
                
                // DB 컬럼명에 맞게 매핑
                facilityDTO.setFacilityTheme((String) productData.get("facility_theme"));
                facilityDTO.setFacilityAddressLocation((String) productData.get("facility_address_location"));
                facilityDTO.setFacilityAddressCity((String) productData.get("facility_address_city"));
                facilityDTO.setFacilityDetailAddress((String) productData.get("facility_detail_address"));
                facilityDTO.setFacilityPhone((String) productData.get("facility_phone"));
                facilityDTO.setFacilityHomepage((String) productData.get("facility_homepage"));
                facilityDTO.setDefaultMessage((String) productData.get("default_message"));
                
                System.out.println("=== 매핑된 FacilityDTO 정보 ===");
                System.out.println("시설명: " + facilityDTO.getFacilityName());
                System.out.println("시설유형: " + facilityDTO.getFacilityType());
                System.out.println("이용료: " + facilityDTO.getFacilityCharge());
                System.out.println("주소: " + facilityDTO.getFacilityDetailAddress());
                
                System.out.println("변환된 FacilityDTO: " + facilityDTO.getFacilityName() + " - " + facilityDTO.getFacilityType());
                
                // 서비스 호출하여 실제 DB에 등록
                facilityService.addFacility(facilityDTO);
                
                Map<String, Object> successResponse = new HashMap<>();
                successResponse.put("success", true);
                successResponse.put("message", "시설이 성공적으로 등록되었습니다.");
                
                System.out.println("=== 기업 등록 완료 ===");
                
                return ResponseEntity.ok()
                    .header("Content-Type", "application/json; charset=UTF-8")
                    .body(successResponse);
            } 
            else {
                Map<String, Object> errorResponse = new HashMap<>();
                errorResponse.put("success", false);
                errorResponse.put("message", "지원하지 않는 상품 유형입니다: " + prodTypeName);
                return ResponseEntity.badRequest().body(errorResponse);
            }
            
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

    @PutMapping("/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable String id, @RequestBody Map<String, Object> updates) {
        try {
            System.out.println("===== 상품 수정 요청 =====");
            System.out.println("ID: " + id);
            System.out.println("업데이트 데이터: " + updates);

            // updatedFields 확인
            if (updates.containsKey("updatedFields")) {
                System.out.println("업데이트할 필드들: " + updates.get("updatedFields"));
            }

            CaregiverDTO caregiverDTO = new CaregiverDTO();
            caregiverDTO.setCaregiverId(id);
            
            // 필드 매핑
            if (updates.containsKey("userGender")) {
                String userGender = (String) updates.get("userGender");
                System.out.println("설정할 userGender: " + userGender);
                caregiverDTO.setUserGender(userGender);
            }
            
            if (updates.containsKey("hope_work_amount")) {
                String amountStr = String.valueOf(updates.get("hope_work_amount"));
                System.out.println("설정할 hope_work_amount: " + amountStr);
                caregiverDTO.setHopeWorkAmount(Integer.parseInt(amountStr));
            }
            
            if (updates.containsKey("introduction")) {
                String intro = (String) updates.get("introduction");
                System.out.println("설정할 introduction: " + intro);
                caregiverDTO.setIntroduction(intro);
            }
            
            if (updates.containsKey("hope_work_area_location")) {
                String location = (String) updates.get("hope_work_area_location");
                System.out.println("설정할 hope_work_area_location: " + location);
                caregiverDTO.setHopeWorkAreaLocation(location);
            }
            
            if (updates.containsKey("hope_work_area_city")) {
                String city = (String) updates.get("hope_work_area_city");
                System.out.println("설정할 hope_work_area_city: " + city);
                caregiverDTO.setHopeWorkAreaCity(city);
            }
            
            if (updates.containsKey("hope_work_place")) {
                String place = (String) updates.get("hope_work_place");
                System.out.println("설정할 hope_work_place: " + place);
                caregiverDTO.setHopeWorkPlace(place);
            }
            
            if (updates.containsKey("hope_work_type")) {
                String type = (String) updates.get("hope_work_type");
                System.out.println("설정할 hope_work_type: " + type);
                caregiverDTO.setHopeWorkType(type);
            }
            
            if (updates.containsKey("hope_employment_type")) {
                String empType = (String) updates.get("hope_employment_type");
                System.out.println("설정할 hope_employment_type: " + empType);
                caregiverDTO.setHopeEmploymentType(empType);
            }

            // prodDetail이 있으면 introduction으로 설정
            if (updates.containsKey("prodDetail")) {
                String detail = (String) updates.get("prodDetail");
                System.out.println("설정할 prodDetail(introduction): " + detail);
                caregiverDTO.setIntroduction(detail);
            }

            System.out.println("변환된 DTO: " + caregiverDTO);
            
            caregiverService.updateCaregiver(id, caregiverDTO);
            
            return ResponseEntity.ok().body(Map.of(
                "success", true,
                "message", "요양사 정보가 성공적으로 수정되었습니다."
            ));
        } catch (Exception e) {
            System.err.println("상품 수정 중 오류 발생: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of(
                        "success", false,
                        "error", "상품 수정 중 오류가 발생했습니다: " + e.getMessage()
                    ));
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json; charset=UTF-8")
    public ResponseEntity<String> deleteProduct(@PathVariable String id) {
        return ResponseEntity.ok("간병사가 성공적으로 삭제되었습니다.");
    }
    
    // 회원 목록 조회 API (실제 DB 연동) - Alternative Path
    @GetMapping(value = "/get-users", produces = "application/json; charset=UTF-8")
    public ResponseEntity<Map<String, Object>> getUsersAlternative(@RequestParam(required = false) String role) {
        try {
            System.out.println("=== 실제 DB에서 회원 목록 조회 요청 (Alternative) ===");
            System.out.println("요청된 role: " + role);
            
            // UserMapper null 체크
            if (userMapper == null) {
                System.err.println("ERROR: userMapper가 null입니다!");
                throw new RuntimeException("UserMapper가 주입되지 않았습니다.");
            }
            
            System.out.println("UserMapper 정상 주입됨");
            
            // role 매개변수에 따라 사용자 조회
            String targetRole = role != null ? role : "CAREGIVER"; // 기본값은 CAREGIVER
            List<User> users = userMapper.findUsersByRole(targetRole);
            System.out.println("DB에서 조회된 " + targetRole + " 사용자 수: " + users.size());
            
            List<Map<String, Object>> userList = new ArrayList<>();
            
            for (User user : users) {
                Map<String, Object> userMap = new HashMap<>();
                userMap.put("member_id", user.getMemberId());
                userMap.put("username", user.getUsername());
                userMap.put("name", user.getName());
                userMap.put("email", user.getEmail());
                userMap.put("phone", user.getPhone());
                
                // role에 따라 추천 상품명 다르게 설정
                String suggestedProductName;
                if ("COMPANY".equals(targetRole)) {
                    suggestedProductName = user.getName() + " 기업";
                } else {
                    suggestedProductName = "요양사 " + user.getName();
                }
                userMap.put("suggested_product_name", suggestedProductName);
                
                userList.add(userMap);
                
                System.out.println("사용자: " + user.getName() + " (" + user.getUsername() + ") - " + targetRole);
            }
            
            Map<String, Object> response = new HashMap<>();
            response.put("success", true);
            response.put("users", userList);
            
            System.out.println("=== 실제 DB 조회 완료 (Alternative): " + userList.size() + "명 ===");
            
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