package com.daview.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.daview.dto.User;
import java.util.List;

@Mapper
public interface UserMapper {
	User findByUsername(String username);
	void insertUser(User user);
	int countByUsername(String username);
	String findUsernameByPhone(@Param("name") String name, @Param("phone") String phone);
	User findUserForReset(@Param("name") String name, @Param("username") String username, @Param("phone") String phone);
	int updatePassword(@Param("username") String username, @Param("password") String password);
	String findUsernameByEmail(@Param("name") String name, @Param("email") String email);
	
	// 실제 DB에서 CAREGIVER 역할 사용자 조회
	List<User> findUsersByRole(@Param("role") String role);


}
