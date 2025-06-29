package com.daview.dto;

import java.time.LocalDateTime;

/**
 * 관리자용 유저 정보 DTO
 * DV_users 테이블의 데이터를 관리자 화면에서 표시하기 위한 데이터 전송 객체
 */
public class AdminUserDto {
    
    private Long memberId;           // member_id (PK)
    private String username;         // 사용자명/아이디
    private String name;             // 실명
    private String email;            // 이메일
    private String phone;            // 전화번호
    private String role;             // 역할 (USER, ADMIN)
    private LocalDateTime createAt;  // 생성일시
    private Integer withdrawn;       // 탈퇴 여부 (0: 활성, 1: 탈퇴)
    
    // 추가 표시용 필드들
    private String statusText;       // 상태 텍스트 (활성/탈퇴)
    private String roleText;         // 역할 텍스트 (한국어)
    
    // 기본 생성자
    public AdminUserDto() {}
    
    // 전체 생성자
    public AdminUserDto(Long memberId, String username, String name, String email, String phone, 
                       String role, LocalDateTime createAt, Integer withdrawn) {
        this.memberId = memberId;
        this.username = username;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.role = role;
        this.createAt = createAt;
        this.withdrawn = withdrawn;
        
        // 텍스트 필드 자동 설정
        this.statusText = (withdrawn != null && withdrawn == 1) ? "탈퇴" : "활성";
        this.roleText = getRoleKorean(role);
    }
    
    // 역할을 한국어로 변환
    private String getRoleKorean(String role) {
        if (role == null) return "미지정";
        switch (role.toUpperCase()) {
            case "USER": return "일반 사용자";
            case "ADMIN": return "관리자";
            default: return role;
        }
    }
    
    // Getter와 Setter 메서드들
    public Long getMemberId() {
        return memberId;
    }
    
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }
    
    public String getUsername() {
        return username;
    }
    
    public void setUsername(String username) {
        this.username = username;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public String getRole() {
        return role;
    }
    
    public void setRole(String role) {
        this.role = role;
        this.roleText = getRoleKorean(role); // 역할 변경 시 텍스트도 업데이트
    }
    
    public LocalDateTime getCreateAt() {
        return createAt;
    }
    
    public void setCreateAt(LocalDateTime createAt) {
        this.createAt = createAt;
    }
    
    public Integer getWithdrawn() {
        return withdrawn;
    }
    
    public void setWithdrawn(Integer withdrawn) {
        this.withdrawn = withdrawn;
        this.statusText = (withdrawn != null && withdrawn == 1) ? "탈퇴" : "활성"; // 상태 변경 시 텍스트도 업데이트
    }
    

    
    public String getStatusText() {
        return statusText;
    }
    
    public void setStatusText(String statusText) {
        this.statusText = statusText;
    }
    
    public String getRoleText() {
        return roleText;
    }
    
    public void setRoleText(String roleText) {
        this.roleText = roleText;
    }
    

    
    @Override
    public String toString() {
        return "AdminUserDto{" +
                "memberId=" + memberId +
                ", username='" + username + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", phone='" + phone + '\'' +
                ", role='" + role + '\'' +
                ", createAt=" + createAt +
                ", withdrawn=" + withdrawn +
                ", statusText='" + statusText + '\'' +
                ", roleText='" + roleText + '\'' +
                '}';
    }
}
