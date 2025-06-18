package com.daview.controller.admin_controller;

import com.daview.dto.ProductDTO;
import com.daview.service.admin_service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/admin/products")
public class AdminProductController {

    @Autowired
    private ProductService productService;

    // 상품 조회 API (prod_type 기준)
    @GetMapping
    public List<ProductDTO> getProductsByType(@RequestParam("type") String type) {
        return productService.getProductsByType(type);
    }
}
