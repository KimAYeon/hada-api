package com.hada.api.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hada.api.model.Video;
import com.hada.api.service.VideoServiceImpl;

@RestController
@RequestMapping(value="/video")
public class VideoRestController {
	@Autowired VideoServiceImpl VideoServiceImpl;
	
	@GetMapping("/getList")
	public List<Video> getVideoList(@RequestParam int cno) {
		return VideoServiceImpl.selectVideoList(cno);
	}
	
	@PostMapping("/saveUrl")
	public int saveVideoUrl(@RequestBody Video video) {
		int result = VideoServiceImpl.insertVideoUrl(video);
		if(result > 0) {
			return video.getVno();
		}
		return 0;
	}
	
	@DeleteMapping("/deleteUrl")
	public int deleteVideoUrl(@RequestParam int vno) {
		return VideoServiceImpl.deleteVideoUrl(vno);
	}
}
