package com.daview.mapper.admin_mapper; // 경로 수정

import com.daview.dto.CaregiverDTO; // DTO 임포트
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface AdminCaregiverMapper {

    @Insert("INSERT INTO caregiver (name, license, experience, price_per_hour, available_region) VALUES (#{name}, #{license}, #{experience}, #{pricePerHour}, #{availableRegion})")
    void insertCaregiver(CaregiverDTO caregiverDTO);

    @Select("SELECT * FROM caregiver")
    List<CaregiverDTO> getAllCaregivers();

    @Update("UPDATE caregiver SET name = #{name}, license = #{license}, experience = #{experience}, price_per_hour = #{pricePerHour}, available_region = #{availableRegion} WHERE id = #{id}")
    void updateCaregiver(CaregiverDTO caregiverDTO);

    @Delete("DELETE FROM caregiver WHERE id = #{id}")
    void deleteCaregiver(Long id);

    @Select("SELECT * FROM caregiver WHERE id = #{id}")
    CaregiverDTO getCaregiverById(Long id);
}
