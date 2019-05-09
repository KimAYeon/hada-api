package com.hada.api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hada.api.mapper.VideoMapper;
import com.hada.api.model.Video;

@Service
public class VideoServiceImpl {
	@Autowired VideoMapper videoMapper;
	
	public List<Video> selectVideoList(int cno) {
		return videoMapper.selectVideoList(cno);
	}
	
	public int insertVideoUrl(Video Video) {
		return videoMapper.insertVideoUrl(Video);
	}
	
	public int deleteVideoUrl(int vno) {
		return videoMapper.deleteVideoUrl(vno);
	}
}
