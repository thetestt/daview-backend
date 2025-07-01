package com.daview.service.admin.impl;

import com.daview.dto.AdminNoticeDTO;
import com.daview.mapper.admin.AdminNoticeMapper;
import com.daview.service.admin.AdminNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class AdminNoticeServiceImpl implements AdminNoticeService {

    @Autowired
    private AdminNoticeMapper adminNoticeMapper;

    @Override
    public Map<String, Object> getNoticeList(Map<String, Object> params) {
        try {
            // 파라미터 맵을 직접 전달 (MyBatis에서 키를 통해 직접 접근)
            List<AdminNoticeDTO> notices = adminNoticeMapper.selectNoticeList(params);
            Long totalCount = adminNoticeMapper.selectNoticeCount(params);
            
            Map<String, Object> result = new HashMap<>();
            result.put("notices", notices);
            result.put("totalCount", totalCount);
            
            return result;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("공지사항 목록 조회 실패", e);
        }
    }

    @Override
    public AdminNoticeDTO getNoticeById(Long id) {
        try {
            return adminNoticeMapper.selectNoticeById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("공지사항 상세 조회 실패", e);
        }
    }

    @Override
    public boolean createNotice(AdminNoticeDTO notice) {
        try {
            // 입력값 검증
            if (notice.getTitle() == null || notice.getTitle().trim().isEmpty()) {
                throw new IllegalArgumentException("제목은 필수입니다.");
            }
            if (notice.getContent() == null || notice.getContent().trim().isEmpty()) {
                throw new IllegalArgumentException("내용은 필수입니다.");
            }
            
            int result = adminNoticeMapper.insertNotice(notice);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("공지사항 등록 실패", e);
        }
    }

    @Override
    public boolean updateNotice(AdminNoticeDTO notice) {
        try {
            // 입력값 검증
            if (notice.getId() == null) {
                throw new IllegalArgumentException("공지사항 ID가 필요합니다.");
            }
            if (notice.getTitle() == null || notice.getTitle().trim().isEmpty()) {
                throw new IllegalArgumentException("제목은 필수입니다.");
            }
            if (notice.getContent() == null || notice.getContent().trim().isEmpty()) {
                throw new IllegalArgumentException("내용은 필수입니다.");
            }
            
            int result = adminNoticeMapper.updateNotice(notice);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("공지사항 수정 실패", e);
        }
    }

    @Override
    public boolean deleteNotice(Long id) {
        try {
            if (id == null) {
                throw new IllegalArgumentException("공지사항 ID가 필요합니다.");
            }
            
            int result = adminNoticeMapper.deleteNotice(id);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("공지사항 삭제 실패", e);
        }
    }

    @Override
    public boolean deleteNotices(List<Long> ids) {
        try {
            if (ids == null || ids.isEmpty()) {
                throw new IllegalArgumentException("삭제할 공지사항 ID 목록이 필요합니다.");
            }
            
            int result = adminNoticeMapper.deleteNotices(ids);
            return result > 0;
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("공지사항 일괄 삭제 실패", e);
        }
    }
} 