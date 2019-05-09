package com.hada.api.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;

import com.hada.api.model.Challenge;

@Mapper
public interface ChallengeMapper {
	public List<Challenge> selectChallengeList(String email);
	public Challenge selectChallengeDetail(int cno);
	public int insertChallengeDetail(Challenge challenge);
	public int updateChallengeDetail(Challenge challenge);
}
