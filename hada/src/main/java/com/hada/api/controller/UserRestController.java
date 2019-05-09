package com.hada.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hada.api.model.User;
import com.hada.api.service.UserServiceImpl;

@RestController
@RequestMapping(value="/user")
public class UserRestController {
	@Autowired UserServiceImpl userServiceImpl;
	
	@GetMapping("/getList")
	public List<User> getUserList(@RequestParam String nickname) {
		return userServiceImpl.selectUserList(nickname);
	}
	
	@GetMapping("/getDetail")
	public User getUserDetail(@RequestParam String email) {
		return userServiceImpl.selectUserDetail(email);
	}
	
	@PostMapping("/getCount")
	public int getUserCount(@RequestBody User user) {
		return userServiceImpl.selectUserCount(user);
	}
	
	@PostMapping("/saveDetail")
	public int saveUserDetail(@RequestBody User user) {
		return userServiceImpl.insertUserDetail(user);
	}
	
	@PutMapping("/modifyDetail")
	public int modifyUserDetail(@RequestBody User user) {
		return userServiceImpl.updateUserDetail(user);
	}
}
