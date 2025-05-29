package com.daview.mapper;

import com.daview.dto.SearchFilterOptionDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SearchFilterOptionMapper {
    List<SearchFilterOptionDTO> getOptionsByCategory(@Param("category") String category);
}
