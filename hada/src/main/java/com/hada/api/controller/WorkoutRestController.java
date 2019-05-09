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
import com.hada.api.service.WorkoutServiceImpl;

@RestController
@RequestMapping(value="/workout")
public class WorkoutRestController {
	@Autowired WorkoutServiceImpl WorkoutServiceImpl;
	
	@GetMapping("/getList")
	public List<Workout> getWorkoutList(@RequestParam String email, int cno) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("email", email);
		map.put("cno", cno);
		
		return WorkoutServiceImpl.selectWorkoutList(map);
	}
	
	@PostMapping("/saveDetail")
	public int saveWorkoutDetail(@RequestBody Workout workout) {
		return WorkoutServiceImpl.insertWorkoutDetail(workout);
	}
	
	@PutMapping("/modifyDetail")
	public int modifyWorkoutDetail(@RequestBody Map<String, Object> param) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("challenge", param.get("challenge"));
		map.put("workout", param.get("workout"));
		
		return WorkoutServiceImpl.updateWorkoutDetail(map);
	}
}
