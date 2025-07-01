package com.daview.service.admin;

import com.daview.dto.AdminNoticeDTO;
import java.util.List;
import java.util.Map;

public interface AdminNoticeService {
    
    /**
     * 공지사항 목록 조회 (페이징, 검색)
     */
    Map<String, Object> getNoticeList(Map<String, Object> params);
    
    /**
     * 공지사항 상세 조회
     */
    AdminNoticeDTO getNoticeById(Long id);
    
    /**
     * 공지사항 등록
     */
    boolean createNotice(AdminNoticeDTO notice);
    
    /**
     * 공지사항 수정
     */
    boolean updateNotice(AdminNoticeDTO notice);
    
    /**
     * 공지사항 삭제
     */
    boolean deleteNotice(Long id);
    
    /**
     * 여러 공지사항 삭제
     */
    boolean deleteNotices(List<Long> ids);
} 