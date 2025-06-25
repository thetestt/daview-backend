package com.daview.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.daview.dto.CouponDTO;
import com.daview.dto.User;
import java.util.List;

import com.daview.dto.UserCoupon;


@Mapper
public interface UserMapper {
	User findByUsername(String username);
	void insertUser(User user);
	int countByUsername(String username);
	String findUsernameByPhone(@Param("name") String name, @Param("phone") String phone);
	User findUserForReset(@Param("name") String name, @Param("username") String username, @Param("phone") String phone);
	int updatePassword(@Param("username") String username, @Param("password") String password);


	String findUsernameByEmail(@Param("name") String name, @Param("email") String email);
	CouponDTO findCouponByCode(String code); // WELCOME 쿠폰 찾기
	int hasUserReceivedCoupon(Map<String, Object> params); // 중복 발급 방지
	void insertUserCoupon(Map<String, Object> params); // 쿠폰 발급
	List<UserCoupon> getCouponsByMemberId(Long memberId); // 마이페이지 쿠폰 목록
	User findByMemberId(Long memberId);



	
	// 실제 DB에서 CAREGIVER 역할 사용자 조회
	List<User> findUsersByRole(@Param("role") String role);
	
	//수안추가 - 채팅상단 정보불러오기용
	User findUserById(@Param("memberId") Long memberId);

}
