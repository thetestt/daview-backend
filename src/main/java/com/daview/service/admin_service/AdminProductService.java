// AdminProductService.java (인터페이스)
package com.daview.service.admin_service;

import com.daview.dto.admin_dto.AdminProductDTO;
import java.util.List;

// 상품 서비스 인터페이스
public interface AdminProductService {
    List<AdminProductDTO> getAll(AdminProductDTO dto); // 목록 조회
    AdminProductDTO getById(String prodId);            // 상세 조회
    void create(AdminProductDTO dto);                  // 등록
    void update(AdminProductDTO dto);                  // 수정
    void delete(String prodId);                        // 삭제
}
