// AdminProductController.java
package com.daview.controller.admin_controller;

import com.daview.dto.admin_dto.AdminProductDTO;
import com.daview.service.admin_service.AdminProductService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/admin/products")
public class AdminProductController {

    private final AdminProductService service;

    public AdminProductController(AdminProductService service) {
        this.service = service;
    }

    // 상품 목록 조회 (페이징 + 검색 포함)
    @GetMapping
    public List<AdminProductDTO> getAll(
        @RequestParam(defaultValue = "0") int page,
        @RequestParam(defaultValue = "10") int size,
        @RequestParam(required = false) String search) {

        AdminProductDTO dto = new AdminProductDTO();
        dto.setPage(page);
        dto.setSize(size);
        dto.setSearch(search);
        return service.getAll(dto);
    }

    // 상품 상세 조회
    @GetMapping("/{id}")
    public AdminProductDTO getById(@PathVariable("id") String id) {
        return service.getById(id);
    }

    // 상품 등록
    @PostMapping
    public void create(@RequestBody AdminProductDTO dto) {
        service.create(dto);
    }

    // 상품 수정
    @PutMapping
    public void update(@RequestBody AdminProductDTO dto) {
        service.update(dto);
    }

    // 상품 삭제
    @DeleteMapping("/{id}")
    public void delete(@PathVariable("id") String id) {
        service.delete(id);
    }
}
