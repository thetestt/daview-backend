package com.daview.mapper.admin;

import com.daview.dto.AdminNoticeDTO;
import org.apache.ibatis.annotations.Mapper;
import java.util.List;
import java.util.Map;

@Mapper
public interface AdminNoticeMapper {
    
    // 공지사항 목록 조회 (페이징, 검색)
    List<AdminNoticeDTO> selectNoticeList(Map<String, Object> params);
    
    // 공지사항 총 개수 조회
    Long selectNoticeCount(Map<String, Object> params);
    
    // 공지사항 상세 조회
    AdminNoticeDTO selectNoticeById(Long id);
    
    // 공지사항 등록
    int insertNotice(AdminNoticeDTO notice);
    
    // 공지사항 수정
    int updateNotice(AdminNoticeDTO notice);
    
    // 공지사항 삭제
    int deleteNotice(Long id);
    
    // 여러 공지사항 삭제
    int deleteNotices(List<Long> ids);
} 