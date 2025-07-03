package com.daview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.daview.dto.CouponDTO;
import com.daview.dto.User;
import com.daview.dto.UserCoupon;

@Mapper
public interface UserMapper {
	User findByUsername(String username);

	void insertUser(User user);

	int countByUsername(String username);

	// 전화번호로 아이디찾기
	String findUsernameByPhone(@Param("name") String name, @Param("phone") String phone);
	User findUserForReset(@Param("name") String name, @Param("username") String username, @Param("phone") String phone);

	// 이메일로 아이디찾기
	String findUsernameByEmail(@Param("name") String name, @Param("email") String email);

	// 현재 비밀번호 확인
	String getPasswordByUsername(String username);

	// 비밀번호 변경
	int updatePassword(@Param("username") String username, @Param("password") String password);

	// 웰컴쿠폰
	CouponDTO findCouponByCode(String code); // WELCOME 쿠폰 찾기
	int hasUserReceivedCoupon(Map<String, Object> params); // 중복 발급 방지
	void insertUserCoupon(Map<String, Object> params); // 쿠폰 발급
	List<UserCoupon> getCouponsByMemberId(Long memberId); // 마이페이지 쿠폰 목록
	int useUserCoupon(Long memberId, Long couponId);
	User findByMemberId(Long memberId);

	// 실제 DB에서 CAREGIVER 역할 사용자 조회
	List<User> findUsersByRole(@Param("role") String role);
	
	//환불계좌
	void updateRefundAccount(
			@Param("username") String username, 
			@Param("bankName") String bankName,
			@Param("accountNumber") String accountNumber);
	void deleteRefundAccount(@Param("username") String username);
	void updateMarketingConsent(@Param("username") String username, @Param("marketingAgreed") boolean marketingAgreed);
	
	//마케팅동의
	void updateAgreeSms(@Param("username") String username, @Param("value") boolean value);
	void updateAgreeEmail(@Param("username") String username, @Param("value") boolean value);
	void updateAgreePush(@Param("username") String username, @Param("value") boolean value);
	
	//회원탈퇴
	void withdrawUser(@Param("username") String username, @Param("reason") String reason);
	
	//아이디변경
	boolean existsByUsername(String username);
    void updateUsername(@Param("currentUsername") String currentUsername,
                        @Param("newUsername") String newUsername);
    
    //탈퇴 시 탈퇴정보 저장
    void markUserAsWithdrawn(@Param("id") Long id, @Param("reason") String reason);

    
    //탈퇴 후 14일간 재가입 금지
    User findWithdrawnUserWithin14Days(String phone);
    
    //탈퇴 후 재가입 시 쿠폰X
    User findOldWithdrawnUser(String phone);
    
    User findActiveUserByPhone(String phone);
    
    void updateProfileImageUrl(@Param("memberId") Long memberId, @Param("imageUrl") String imageUrl);
    
    String findProfileImageByMemberId(Long memberId);
    
    void updateProfileImage(@Param("memberId") Long memberId, @Param("path") String path);




	// 수안추가 - 채팅상단 정보불러오기용
	//User findUserById(@Param("memberId") Long memberId);
	User findByMemberId2(@Param("memberId") Long memberId);
	User findByMemberId3(@Param("memberId") Long memberId);
	
	String findNameByMemberId(@Param("memberId") Long memberId);

}
