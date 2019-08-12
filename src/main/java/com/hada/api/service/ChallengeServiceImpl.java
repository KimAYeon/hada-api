package com.hada.api.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hada.api.exception.HadaApiErrorCode;
import com.hada.api.exception.HadaApiException;
import com.hada.api.exception.HadaBadRequestException;
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
		if(challenge.getReq_email().equals(challenge.getRes_email())) {
			throw new HadaBadRequestException("챌린지 요청자와 응답자가 동일합니다.", HadaApiErrorCode.SELF_REQUEST);
		}
		return challengeMapper.insertChallengeDetail(challenge);
	}
	
	public int updateChallengeDetail(Challenge challenge) {
		return challengeMapper.updateChallengeDetail(challenge);
	}
	
	public int deleteChallengeDetail(int cno) {
		return challengeMapper.deleteChallengeDetail(cno);
	}
	
	public List<Map<String, Object>> selectChallengeHistory(Map<String, Object> param) {
		return challengeMapper.selectChallengeHistory(param);
	}
}
