package com.hada.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.hada.api.model.Challenge;
import com.hada.api.model.Workout;

@Mapper
public interface ChallengeMapper {
	public List<Challenge> selectChallengeList(Challenge challenge);
	public Challenge selectChallengeDetail(int cno);
	public int insertChallengeDetail(Challenge challenge);
	public int updateChallengeDetail(Challenge challenge);
	public int updateChallengeByWorkout(Workout workout);
	public int deleteChallengeDetail(int cno);
	public List<Map<String, Object>> selectChallengeHistory(Challenge challenge);
}
