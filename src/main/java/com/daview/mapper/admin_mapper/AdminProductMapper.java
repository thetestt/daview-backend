package com.daview.mapper.admin_mapper;

import com.daview.dto.admin_dto.AdminProductDTO;
import java.util.List;

// 상품 Mapper 인터페이스 (MyBatis SQL 연결)
public interface AdminProductMapper {
    List<AdminProductDTO> findAll(AdminProductDTO dto); // 페이징 + 검색
    AdminProductDTO findById(String prodId);            // 단건 조회
    void insert(AdminProductDTO dto);                   // 등록
    void update(AdminProductDTO dto);                   // 수정
    void delete(String prodId);                         // 삭제 (is_deleted 변경)
}
