package com.daview.service.admin_service;

import com.daview.dto.ProductDTO;
import java.util.List;

public interface ProductService {

    List<ProductDTO> getProductsByType(String type);
}
