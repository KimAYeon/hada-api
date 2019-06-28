package com.hada.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.hada.api.mapper.UserMapper;
import com.hada.api.model.User;

@Service
public class UserServiceImpl {
	@Autowired UserMapper userMapper;
	
	public List<User> selectUserList(String search) {
		return userMapper.selectUserList(search);
	}
	
	public User selectUserDetail(String email) {
		return userMapper.selectUserDetail(email);
	}
	
	public int selectUserCount(User user) {
		return userMapper.selectUserCount(user);
	}
	
	public int insertUserDetail(User user) {
		if(!ObjectUtils.isEmpty(selectUserDetail(user.getEmail()))) {
			throw new IllegalArgumentException("�̹� �����ϴ� �̸��� �Դϴ�.");
		}
		return userMapper.insertUserDetail(user);
	};
	
	public int updateUserDetail(User user) {
		return userMapper.updateUserDetail(user);
	};
}
