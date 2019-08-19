package com.hada.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.hada.api.common.EncryptUtil;
import com.hada.api.exception.HadaApiErrorCode;
import com.hada.api.exception.HadaBadRequestException;
import com.hada.api.exception.HadaEncryptException;
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
	
	public User selectUserForLogin(User user) {
		User targetUser = userMapper.selectUserDetail(user.getEmail());
		String targetPwd = null;
		
		try {
			targetPwd = EncryptUtil.decryptAES256(targetUser.getPassword());
		} catch (Exception e) {
			throw new HadaEncryptException("��ȣȭ�� �����Ͽ����ϴ�.", HadaApiErrorCode.FAIL_DECRYPT);
		}
		
		if(!ObjectUtils.isEmpty(targetUser) && targetPwd.equals(user.getPassword())) {
			targetUser.setPassword(null);
			return targetUser;
		} 
		
		return null;
	}
	
	public int insertUserDetail(User user) {
		if(!ObjectUtils.isEmpty(selectUserDetail(user.getEmail()))) {
			throw new HadaBadRequestException("�̹� �����ϴ� �̸��� �Դϴ�.", HadaApiErrorCode.EXISTED_EMAIL);
		}
		
		try {
			user.setPassword(EncryptUtil.encryptAES256(user.getPassword()));
		} catch (Exception e) {
			throw new HadaEncryptException("��ȣȭ�� �����Ͽ����ϴ�.", HadaApiErrorCode.FAIL_ENCRYPT);
		}
		
		return userMapper.insertUserDetail(user);
	};
	
	public int updateUserDetail(User user) {
		try {
			user.setPassword(EncryptUtil.encryptAES256(user.getPassword()));
		} catch (Exception e) {
			throw new HadaEncryptException("��ȣȭ�� �����Ͽ����ϴ�.", HadaApiErrorCode.FAIL_ENCRYPT);
		}
		
		return userMapper.updateUserDetail(user);
	};
}
