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
	User findByMemberId(Long memberId);

	// 실제 DB에서 CAREGIVER 역할 사용자 조회
	List<User> findUsersByRole(@Param("role") String role);
	
	//환불계좌
	void updateRefundAccount(
			@Param("username") String username, 
			@Param("bankName") String bankName,
			@Param("accountNumber") String accountNumber);
	User getRefundAccount(@Param("username") String username);
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



	// 수안추가 - 채팅상단 정보불러오기용
	//User findUserById(@Param("memberId") Long memberId);
	User findByMemberId2(@Param("memberId") Long memberId);
	User findByMemberId3(@Param("memberId") Long memberId);

}
