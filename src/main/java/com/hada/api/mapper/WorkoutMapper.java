package com.hada.api.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.hada.api.model.Challenge;
import com.hada.api.model.Workout;

@Mapper
public interface WorkoutMapper {
	public List<Workout> selectWorkoutList(Map<String, Object> param);
	public Workout selectWorkoutDetail(int wno);
	public Workout selectWorkoutLatest(Map<String, Object> param);
	public int insertWorkoutDetail(Workout Workout);
	public int updateWorkoutDetail(Workout Workout);
}
