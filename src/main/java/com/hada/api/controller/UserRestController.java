package com.hada.api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.hada.api.model.User;
import com.hada.api.service.S3Uploader;
import com.hada.api.service.UserServiceImpl;

@RestController
@RequestMapping(value="/user")
public class UserRestController {
	@Autowired UserServiceImpl userServiceImpl;
	@Autowired S3Uploader s3Uploader;
	
	@GetMapping("/getList")
	public List<User> getUserList(@RequestParam String search) {
		return userServiceImpl.selectUserList(search);
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
	
	@PostMapping("/uploadProfile")
	@ResponseBody
	public String uploadProfile(@RequestParam("profile") MultipartFile multipartFile, @RequestParam("email") String email) throws IOException {
		return s3Uploader.upload(multipartFile, email);
	}
}
