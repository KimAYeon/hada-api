package com.hada.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hada.api.model.User;

@Mapper
public interface UserMapper {
	public List<User> selectUserList(String search);
	public User selectUserDetail(String email);
	public int selectUserCount(User user);
	public int insertUserDetail(User user);
	public int updateUserDetail(User user);
}
