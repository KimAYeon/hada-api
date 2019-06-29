package com.hada.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import com.hada.api.model.Workout;
import com.hada.api.service.ChallengeServiceImpl;
import com.hada.api.service.WorkoutServiceImpl;

@RestController
@RequestMapping(value="/workout")
public class WorkoutRestController {
	@Autowired WorkoutServiceImpl WorkoutServiceImpl;
	
	@GetMapping("/getDetail")
	public Workout getDetailLatest(@RequestParam int wno) {
		return WorkoutServiceImpl.selectWorkoutDetail(wno);
	}
	
	@GetMapping("/getDetailLatest")
	public Workout getDetailLatest(@RequestParam int cno, @RequestParam String email) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cno", cno);
		param.put("email", email);
		return WorkoutServiceImpl.selectWorkoutLatest(param);
	}
	
	@PostMapping("/saveDetail")
	public int saveWorkoutDetail(@RequestBody Workout workout) {
		int result = WorkoutServiceImpl.insertWorkoutDetail(workout);
		if(result > 0) {
			return workout.getWno();
		}
		return 0;
	}
	
	@PutMapping("/modifyDetail")
	public int modifyWorkoutDetail(@RequestBody Workout workout) {
		int result = WorkoutServiceImpl.updateWorkoutDetail(workout);
		if(result > 0) {
			return workout.getWno();
		}
		return 0;
	}
}
