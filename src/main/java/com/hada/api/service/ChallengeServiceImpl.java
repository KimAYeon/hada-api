package com.hada.api.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hada.api.mapper.ChallengeMapper;
import com.hada.api.model.Challenge;
import com.hada.api.model.Workout;

@Service
public class ChallengeServiceImpl {
	@Autowired ChallengeMapper challengeMapper;
	
	public List<Challenge> selectChallengeList(Challenge challenge) {
		return challengeMapper.selectChallengeList(challenge);
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
	
	public int updateChallengeByWorkout(Workout workout) {
		return challengeMapper.updateChallengeByWorkout(workout);
	}
	
	public int deleteChallengeDetail(int cno) {
		return challengeMapper.deleteChallengeDetail(cno);
	}
	
	public List<Map<String, Object>> selectChallengeHistory(Challenge challenge) {
		return challengeMapper.selectChallengeHistory(challenge);
	}
}
