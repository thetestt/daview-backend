package com.daview.service.admin_service;

import com.daview.dto.ProductDTO;
import com.daview.mapper.admin_mapper.ProductMapper;  // 새로 추가된 ProductMapper
import com.daview.service.admin_service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Override
    public List<ProductDTO> getProductsByType(String type) {
        return productMapper.getProductsByType(type);
    }
}
