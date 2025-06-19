// ✅ 매퍼 인터페이스 추가
// 📁 src/main/java/com/daview/mapper/admin/AdminFacilityMapper.java

package com.daview.mapper.admin;

import com.daview.dto.FacilityDTO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface AdminFacilityMapper {
	
    List<FacilityDTO> selectAll();
    
    void insert(FacilityDTO dto);
    
    void update(FacilityDTO dto);
    
    void delete(@Param("id") Long id);
}
