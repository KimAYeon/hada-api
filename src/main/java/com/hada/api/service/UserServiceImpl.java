package com.hada.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import com.hada.api.common.EncryptUtil;
import com.hada.api.exception.HadaApiErrorCode;
import com.hada.api.exception.HadaBadRequestException;
import com.hada.api.exception.HadaTokenException;
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
	
	public User selectUserCount(User user) {
		User targetUser = selectUserDetail(user.getEmail());
		String targetPassword = "";
		
		if(!ObjectUtils.isEmpty(targetUser)) {
			try {
				targetPassword = EncryptUtil.decryptAES256(targetUser.getPassword());
			} catch (Exception e) {
				throw new HadaTokenException("��й�ȣ ��ȣȭ�� �����Ͽ����ϴ�.", HadaApiErrorCode.INVALID_TOKEN);
			}
			
			if(user.getPassword().equals(targetPassword)) {
				targetUser.setPassword(null);
				return targetUser;
			}
		}
		
		return null;
	}
	
	public int insertUserDetail(User user) {
		// �̸��� �ߺ� üũ
		if(!ObjectUtils.isEmpty(selectUserDetail(user.getEmail()))) {
			throw new HadaBadRequestException("�̹� �����ϴ� �̸��� �Դϴ�.", HadaApiErrorCode.EXISTED_EMAIL);
		}
		
		// ��й�ȣ ��ȣȭ
		user = encryptUserPassword(user);
		
		return userMapper.insertUserDetail(user);
	};
	
	public int updateUserDetail(User user) {
		// ��й�ȣ ��ȣȭ
		if(!StringUtils.isEmpty(user.getPassword())) {
			user = encryptUserPassword(user);
		}
		
		return userMapper.updateUserDetail(user);
	};
	
	public User encryptUserPassword(User user) {
		try {
			user.setPassword(EncryptUtil.encryptAES256(user.getPassword()));
		} catch (Exception e) {
			throw new HadaTokenException("��й�ȣ ��ȣȭ�� �����Ͽ����ϴ�.", HadaApiErrorCode.INVALID_TOKEN);
		}
		return user;
	}
}
