package com.hada.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hada.api.mapper.ChallengeMapper;
import com.hada.api.model.Challenge;

@Service
public class ChallengeServiceImpl {
	@Autowired ChallengeMapper challengeMapper;
	
	public List<Challenge> selectChallengeList(String email) {
		return challengeMapper.selectChallengeList(email);
	}
	
	public Challenge selectChallengeDetail(int cno) {
		return challengeMapper.selectChallengeDetail(cno);
	}
	
	public int insertChallengeDetail(Challenge challenge) {
		return challengeMapper.insertChallengeDetail(challenge);
	}
	
	public int updateChallengeDetail(Challenge challenge) {
		return challengeMapper.updateChallengeDetail(challenge);
	}
}
