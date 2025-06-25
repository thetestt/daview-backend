package com.daview.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.daview.dto.User;
import com.daview.mapper.UserMapper;

@Service
public class MyPageService {
	@Autowired
    private UserMapper userMapper;

    public User getUserProfile(Long memberId) {
        return userMapper.findByMemberId(memberId);
    }
}
