package com.daview.service.admin;

import com.daview.dto.ProductDTO;
import java.util.List;

public interface ProductService {

    List<ProductDTO> getProductsByType(String type);
}
