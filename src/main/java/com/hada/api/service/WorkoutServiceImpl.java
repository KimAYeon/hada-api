package com.hada.api.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hada.api.mapper.WorkoutMapper;
import com.hada.api.model.Workout;

@Service
public class WorkoutServiceImpl {
	@Autowired WorkoutMapper workoutMapper;
	
	public List<Workout> selectWorkoutList(Map<String, Object> map) {
		return workoutMapper.selectWorkoutList(map);
	}
	
	public int insertWorkoutDetail(Workout workout) {
		return workoutMapper.insertWorkoutDetail(workout);
	}
	
	public int updateWorkoutDetail(Map<String, Object> map) {
		return workoutMapper.updateWorkoutDetail(map);
	}
}
