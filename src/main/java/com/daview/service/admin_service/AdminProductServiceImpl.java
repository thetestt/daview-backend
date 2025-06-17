// AdminProductServiceImpl.java (구현체)
package com.daview.service.admin_service;

import com.daview.dto.admin_dto.AdminProductDTO;
import com.daview.mapper.admin_mapper.AdminProductMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class AdminProductServiceImpl implements AdminProductService {
    private final AdminProductMapper mapper;

    @Autowired
    public AdminProductServiceImpl(AdminProductMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public List<AdminProductDTO> getAll(AdminProductDTO dto) {
        return mapper.findAll(dto);
    }

    @Override
    public AdminProductDTO getById(String prodId) {
        return mapper.findById(prodId);
    }

    @Override
    public void create(AdminProductDTO dto) {
        dto.setProdId(UUID.randomUUID().toString()); // UUID로 상품 ID 자동 생성
        mapper.insert(dto);
    }

    @Override
    public void update(AdminProductDTO dto) {
        mapper.update(dto);
    }

    @Override
    public void delete(String prodId) {
        mapper.delete(prodId); // is_deleted = 1 처리
    }
}
