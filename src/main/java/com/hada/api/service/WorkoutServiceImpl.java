package com.hada.api.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hada.api.mapper.WorkoutMapper;
import com.hada.api.model.Challenge;
import com.hada.api.model.Workout;

@Service
public class WorkoutServiceImpl {
	@Autowired WorkoutMapper workoutMapper;
	
	public List<Workout> selectWorkoutList(Map<String, Object> param) {
		return workoutMapper.selectWorkoutList(param);
	}
	
	public Workout selectWorkoutDetail(int wno) {
		return workoutMapper.selectWorkoutDetail(wno);
	}
	
	public int insertWorkoutDetail(Workout workout) {
		return workoutMapper.insertWorkoutDetail(workout);
	}
	
	public int updateWorkoutDetail(Workout Workout) {
		return workoutMapper.updateWorkoutDetail(Workout);
	}
}
