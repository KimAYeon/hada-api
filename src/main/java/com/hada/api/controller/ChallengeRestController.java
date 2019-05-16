package com.hada.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hada.api.model.Challenge;
import com.hada.api.service.ChallengeServiceImpl;

@RestController
@RequestMapping(value="/challenge")
public class ChallengeRestController {
	@Autowired ChallengeServiceImpl challengeServiceImpl;
	
	@GetMapping("/getList")
	public List<Challenge> getChallengeList(@RequestParam String email) {
		return challengeServiceImpl.selectChallengeList(email);
	}
	
	@GetMapping("/getDetail")
	public Challenge getChallengeDetail(@RequestParam int cno) {
		return challengeServiceImpl.selectChallengeDetail(cno);
	}
	
	@PostMapping("/saveDetail")
	public int saveChallengeDetail(@RequestBody Challenge challenge) {
		return challengeServiceImpl.insertChallengeDetail(challenge);
	}
	
	@PutMapping("/modifyDetail")
	public int modifyChallengeDetail(@RequestBody Challenge challenge) {
		return challengeServiceImpl.updateChallengeDetail(challenge);
	}
}
