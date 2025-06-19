package com.daview.mapper.admin;

import com.daview.dto.ProductDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import java.util.List;

@Mapper
public interface ProductMapper {

    List<ProductDTO> getProductsByType(String type);
}
