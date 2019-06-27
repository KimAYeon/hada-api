package com.hada.api.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
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
@RequestMapping(value="/challenge")
public class ChallengeRestController {
	@Autowired ChallengeServiceImpl challengeServiceImpl;
	@Autowired WorkoutServiceImpl WorkoutServiceImpl;
	
	@GetMapping("/getList")
	public List<Challenge> getChallengeList(@ModelAttribute Challenge challenge) {
		return challengeServiceImpl.selectChallengeList(challenge);
	}
	
	@GetMapping("/getDetail")
	public Challenge getChallengeDetail(@RequestParam int cno) {
		return challengeServiceImpl.selectChallengeDetail(cno);
	}
	
	@PostMapping("/saveDetail")
	public int saveChallengeDetail(@RequestBody Challenge challenge) {
		int result = challengeServiceImpl.insertChallengeDetail(challenge);
		if(result > 0) {
			return challenge.getCno();
		}
		return result;
	}
	
	@PutMapping("/modifyDetail")
	public int modifyChallengeDetail(@RequestBody Challenge challenge) {
		return challengeServiceImpl.updateChallengeDetail(challenge);
	}
	
	@PutMapping("/modifyIntegratedData")
	public int modifyChallengeIntegratedData(@RequestParam("cno") int cno, @RequestParam("email") String email) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("cno", cno);
		param.put("email", email);
		List<Workout> workoutList = WorkoutServiceImpl.selectWorkoutList(param);
		
		return challengeServiceImpl.updateChallengeByWorkout(workoutList.get(0));
	}
	
	@DeleteMapping("/deleteDetail")
	public int deleteChallengeDetail(@RequestParam int cno) {
		return challengeServiceImpl.deleteChallengeDetail(cno);
	}
	
	@GetMapping("/getHistory")
	public List<Map<String, Object>> getWorkoutList(@RequestParam int cno) {
		Challenge challenge = challengeServiceImpl.selectChallengeDetail(cno);
		return challengeServiceImpl.selectChallengeHistory(challenge);
	}
}
